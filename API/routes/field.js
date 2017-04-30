var facebook = require('fb');

module.exports = function(app, db) {
	var Field = require('../models/field');

    app.get('/field', function(req, res) {
        Field.find(function (err, fields) {
        	if (err) 
        		res.send(err);

        	res.json(fields);
        })
    });

    app.post('/field/create', function(req, res) {
        res.send("success");

        var data = JSON.parse(req.body.data);
        var field = new Field(data);
        field.save();
    });

    app.get('/field/read/:field_id', function(req, res) {
        Field.findById(req.params.field_id, function (err, field) {
        	if (err) 
        		res.send(err);

        	res.json(field);
        })
    });

    app.get('/field/get/:field_name', function(req, res) {
    	Field.find({ 'email' : req.params.field_name }, function (err, field) {
        	if (err) 
        		res.send(err);

        	res.json(field);
        })
    });

    app.get('/field/delete/:field_id', function(req, res) {
        Field.findByIdAndRemove(req.params.field_id, function (err) {
        	if (err) 
        		res.send(err);

        	res.send('success');
        })
    });
}