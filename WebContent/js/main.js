/**
 * This js file includes activity of creating event and sending it to server.
 */
/* Send method */
var host = "http://54.81.51.121:8080";
$("#btn_send").click(function() {
	var timeslots = ""; /* a string to store all time slots */
	var email = $("#email").val();
	var name = $("#name").val();
	var event = $("#event").val();
	var location = $("#location").val();
	console.log("Send!");
	/* Get all time slot div and store in an array */
	var ts = $("#sec_time div");
	console.log(ts.length);
	/* Iterate array to get all time slots, store time slots into a single string 
	 * for easy parameter passing.
	 * */
	for (var i = 0; i<ts.length; i++){
		var date = $("#sec_time div #date").eq(i).val();
		var from = $("#sec_time div #from").eq(i).val();
		var to = $("#sec_time div #to").eq(i).val();
		timeslots += date +" "+ from +" - "+ to + ",";
	}
	console.log(timeslots);
	/* Call restful services and pass event information to the server */
	$.ajax({
		url : host + "/doodlelike/services/schd/sendSched",
		data : {
			email : email,
			name : name,
			event : event,
			location : location,
			time : timeslots
		},
		success : function(data) {
			console.log("Send email....." + data);
			$("#main").load(host + "/doodlelike/pages/create_success.html");
			console.log("back to home page");
		}
	});
});
/**
 * Add new time slot for the event by clone and append the last time slot.
 */
var idx = 0;
$("#btn_addtime").click(function(){
//	var date = $("#sec_time div:last #date").val();
//	var from = $("#sec_time div:last #from").val();
//	var to = $("#sec_time div:last #to").val();
//	timeslots[idx] = date +" "+ from +" - "+ to + ",";
//	idx++;
	$("#sec_time div:last").clone().appendTo("#sec_time");
//	console.log(timeslots);
});
