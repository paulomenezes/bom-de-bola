var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');
var path = require('path')
var multer = require('multer');
var fs = require('fs');
var csv = require("fast-csv");

mongoose.connect('mongodb://localhost/bomdebola');
var db = mongoose.connection;

var app = express();
app.use(bodyParser.json());       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
	extended: true
}));
app.use('/uploads', express.static('uploads'));
app.use(multer({dest: './uploads/', 
	rename: function(fieldname, filename) {
		return filename;
	},
	onFileUploadStart: function(file) {
		console.log(file.originalname + ' is starting...')
	}, 
	onFileUploadCoplete: function(file) {
		console.log(file.fieldname + ' uploaded to ' + file.path)
	}
}));

var port = 8000;

require('./routes/user')(app, db);
require('./routes/post')(app, db);
require('./routes/field')(app, db);
require('./routes/game')(app, db);
require('./routes/championship')(app, db);
require('./routes/team')(app, db);

app.listen(port);

console.log("Server running at port 8000")