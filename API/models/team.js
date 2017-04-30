var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TeamSchema = new Schema({
	rpa: String,
	name: String,
	idManager: String,
	idChampionship: [String],
});

module.exports = mongoose.model('Team', TeamSchema);