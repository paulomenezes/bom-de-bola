var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var FieldSchema = new Schema({
	name: String,
	address: String,
	district: String,
	latitude: String,
	longitude: String,
	type: String,
	rpa: String,
});

module.exports = mongoose.model('Field', FieldSchema);