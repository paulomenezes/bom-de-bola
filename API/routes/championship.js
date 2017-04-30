var facebook = require('fb');

module.exports = function(app, db) {
	var Championship = require('../models/championship');

    app.get('/championship', function(req, res) {
        Championship.find(function (err, championships) {
        	if (err) 
        		res.send(err);

        	res.json(championships);
        })
    });

    app.post('/championship/create', function(req, res) {
        res.send("success");

        var data = JSON.parse(req.body.data);
        var championship = new Championship(data);
        championship.save();
    });

    app.get('/championship/read/:id_manager', function(req, res) {
        Championship.findById(req.params.id_manager, function (err, championship) {
        	if (err) 
        		res.send(err);

        	res.json(championship);
        })
    });

    app.get('/championship/get/:championship_name', function(req, res) {
    	Championship.find({ 'name' : req.params.championship_name }, function (err, championship) {
        	if (err) 
        		res.send(err);

        	res.json(championship);
        })
    });

    app.get('/championship/delete/:championship_id', function(req, res) {
        Championship.findByIdAndRemove(req.params.championship_id, function (err) {
        	if (err) 
        		res.send(err);

        	res.send('success');
        })
    });
}