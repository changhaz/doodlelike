/**
 * 
 */
var col;
var id = $('body').attr('id');
console.log(id);
// var url = "http://localhost:8080/doodlelike/services/xmuchen";
$(document).ready(function() {
	$("#save").click(function() {
		//alert("save");
	});
	$("#delete").click(function() {
		//alert("delete!");
	});
});

$.ajax({
	url : "http://localhost:8080/doodlelike/services/schedule/" + id,
	success : function(data) {
		col = data.time.length;
		var event = data.event;
		var location = data.location;
		$("#event").html(event);
		$("#location").html(location);
		var tbl = "<tr><td></td><td></td><td>Name</td>";
		for (var i = 0; i < col; i++) {
			tbl += "<td>" + data.time[i] + "</td>";
		}
		tbl += "</tr>";
		/** add attendees */
		if (data.attendees != null) {
			for (var i = 0; i < data.attendees.length; i++) {
				tbl += addAttendees(data.attendees[i]);
			}
		}
		$("table").html(tbl);
		$("tr :input").prop("readonly", true);
		$("tr :checkbox").attr("disabled", true);

		/**
		 * Add an input row
		 */
		$("table").append(addrow());
	}
});
var addAttendees = function(attendees) {
	var atdinfo = attendees.split(" ");
	console.log("atdinfo: " + atdinfo);
	var row = "<tr><td><button id='btn_edit' onclick='del()'>Edit</button></td><td><button id='btn_del' onclick='del()'>Delete</button></td>";
	row += "<td><input type='text' value='" + atdinfo[0] + "'/></td>";
	for (var i = 0; i < col; i++) {
		if (contains(atdinfo, i)) {
			console.log("contains");
			row += "<td><input type='checkbox' checked/></td>";
		} else
			row += "<td><input type='checkbox' /></td>";
	}
	row += "</tr>";
	return row;
};
var contains = function(arr, obj) {
	var a = arr;

	for (var m = 0; m < a.length; m++) {
		var n = a[m];
		console.log(m + "," + obj);
		if (n == obj)
			return true;
		// else return false; /** Put this line before, silly mistake!! */
	}
	return false;
};
var addrow = function() {
	var row = "<tr><td><button id='btn_edit' onclick='del()'>Edit</button></td><td><button id='btn_del' onclick='del()'>Delete</button></td>";
	row += "<td><input type='text'/></td>";
	for (var i = 0; i < col; i++) {
		row += "<td><input type='checkbox'/></td>";
	}
	row += "</tr>";
	return row;
};
var del = function() {
	console.log("delete!");
};
var save = function() {
	/**
	 * Save the row.
	 */
	;
//	var name = $("tr:last :input").get(2);
//	$("tr:last :input").attr("value", name.value);
	var rowinfo = $("tr:last :input").eq(2).prop("value"); /* Name input */
	for (var i = 0; i < col; i++) {
		if ($("tr:last :input").eq(i + 3).prop("checked") == true)
			rowinfo = rowinfo + " " + i;
	}
	$("tr:last :input").prop("readonly", true);

//	$("tr:last :checkbox").eq(1).attr("checked", true);
	$("tr:last :checkbox").attr("disabled", true);
	// var checkbox = $("tr:last :checkbox");
//	console.log($("tr:last :checkbox").eq(1).attr('checked'));
	/**
	 * Add a row below.
	 */
	// var tbl = $("table").html();
	$("table").append(addrow());
	console.log("save!" + rowinfo);
	var url = "http://localhost:8080/doodlelike/services/save";
	$.ajax({
		url : url,
		data : {
			id : id,
			update : rowinfo
		},
		success : function(data) {

		}
	});

};