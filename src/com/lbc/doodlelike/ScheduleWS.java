package com.lbc.doodlelike;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.bson.types.ObjectId;

import com.lbc.doodlelike.service.DLServiceImpl;
import com.lbc.doodlelike.service.IDLService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@Path("/")
public class ScheduleWS {
	private IDLService dlservice;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String home() {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("<!DOCTYPE html><html><body id = '")
				.append("'></body><script src='http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js'></script> <script src='../js/main.js'>")
				.append("</script><script>$('body').load('../index.html')</script></html>");
		return null;
	}

	/**
	 * Save the updated attendees' information to database.
	 * 
	 * @param ui
	 *            . UriInfo object contains updates info passed by ajax calls.
	 * @return Status of the activity.
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/save")
	@Produces(MediaType.TEXT_PLAIN)
	public String save(@Context UriInfo ui) {
		MongoClient mongoClient;
		MultivaluedMap<String, String> qParams = ui.getQueryParameters();
		String id = qParams.getFirst("id");
		String update = qParams.getFirst("update");
		try {
			/* connect to mongodb */
			mongoClient = new MongoClient("54.81.51.121");
			/* get database by name */
			DB db = mongoClient.getDB("mydb");
			// get document
			DBCollection coll = db.getCollection("scheduletmp");
			// Create a dbobject query by id
			BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
			/* create a new dboject for update */
			BasicDBObject updatedbo = new BasicDBObject();
			/* Use DBCursor for query and get the first result */
			DBCursor cursor = coll.find(query);
			BasicDBObject dbo = (BasicDBObject) cursor.next();
			List<String> atd = new ArrayList<String>();
			/* Pass the attendees list of first result to a List */
			atd = (List<String>) dbo.get("attendees");
			if (atd != null)
				for (String str : atd) {
					System.out.println("Attendees: " + str);
				}
			/* Update the list and save back to dbobject */
			atd.add(update);
			updatedbo.append("$set", new BasicDBObject("attendees", atd));
			coll.update(query, updatedbo);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "Saved!";
	}

	@GET
	@Path("/schedule/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getScheduleById(@PathParam("id") String id) {
		// ScheduleInfo schedule = @PathParam("id") String id
//		String[] time = { "8:00 AM - 9:00 AM", "9:30 AM - 10:30 AM",
//				"11:30 AM - 12:30 PM" };
		// schedule.setEvent("Plan for Sunday");
		// schedule.setLocation("San Jose");
		// schedule.setTime(time);
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient("54.81.51.121");
			DB db = mongoClient.getDB("mydb");
			DBCollection coll = db.getCollection("scheduletmp");
			BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
			DBCursor cursor = coll.find(query);
			return cursor.next().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		// return new ScheduleInfo("Plan for Sunday", "San Jose");
	}

	/**
	 * Send a notifying htmled email to event scheduler.
	 * 
	 * @param ui
	 * @return
	 */
	@GET
	@Path("/sendSched")
	public String sendSchedule(@Context UriInfo ui) {
		MultivaluedMap<String, String> qParams = ui.getQueryParameters();
		String email = qParams.getFirst("email");
		String name = qParams.getFirst("name");
		String event = qParams.getFirst("event");
		String location = qParams.getFirst("location");
		String time[] = qParams.getFirst("time").split(",");
		String content = "You receive a invitation from " + name + " (" + email
				+ ")" + ". The event is" + event + ", the location is: "
				+ location + " at " + time;
		ObjectId id = new ObjectId();
		dlservice = new DLServiceImpl();
		System.out.println("******:" + time.toString());
		dlservice.createEvent(id, event, location, time);
		String url = "http://54.81.51.121/doodlelike/services/"
				+ id.toString();
		String timestr = "";
		for(String str: time){
			timestr += str + " ";
		}
		String divstyle = "style='text-align: center; position: relative; margin: 50px; padding: 40px; top: 30%; width: 500px; height: auto; background-color: #990000; top: 0; left: 0; margin-left: auto; margin-right: auto; color: white; font-size: 24px;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;'";
		StringBuffer emailcontent = new StringBuffer();
		emailcontent
				.append("<html><body style=''><div ")
				.append(divstyle + ">")
				.append("<h1>FakeDoodle.com</h1>")
				.append("<h3>You have created an event: </h3>")
				.append("<p>Event: " + event + "</p>")
				.append("Location: " + location + "</p>")
				.append("<p>Time: " + timestr + "</p>")
				.append("<a style='color: white' href='" + url
						+ "'>Go to Polling</a>"
						+ "<p>Share this link to invite people: "
						+ url
						+ "</p>"
						+ "</div></body></html>");
		// try {
		// content = IOUtils
		// .toString(new URL(
		// "http://localhost:8080/doodlelike/pages/emailcontent.html"));
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		apachemail(emailcontent.toString(), email);
		return content;

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_HTML)
	public String hello(@PathParam("id") String id) throws IOException {
		// InputStream myhtml = getClass().getClassLoader().getResourceAsStream(
		// "../pages/emailcontent.html");
		if (id != null) {
			return "<!DOCTYPE html><html><body id = '"
					+ id
					+ "'></body><script src='http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js'></script> <script src='../js/main.js'>"
					+ "</script><script>$('body').load('../pages/schedule.html')</script></html>";
		} else
			return "<html><p>Who are you!</p></html>";
	}

	@GET
	@Path("/send")
	@Produces(MediaType.TEXT_PLAIN)
	public String SendEmail() {
//		apachemail("Testing");
		return "sent";
	}

	public static void apachemail(String content, String to) {
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("lbchencn@gmail.com",
				"Fight#88@02"));
		email.setSSLOnConnect(true);
		try {
			email.setFrom("lbchencn@gmail.com");
			email.setSubject("TestMail");
			email.setHtmlMsg(content);
			email.addTo(to);
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
