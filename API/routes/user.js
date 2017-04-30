var facebook = require('fb');

module.exports = function(app, db) {
	var User = require('../models/user');

    app.get('/user', function(req, res) {
        User.find().sort([['imageCover', -1]]).limit(10).exec(function (err, users) {
        	if (err) 
        		res.send(err);

        	res.json(users);
        })
    });

    app.post('/user/create', function(req, res) {
        res.send("success");

        var data = JSON.parse(req.body.data);
        var user = new User(data);
        user.save();
    });

    app.get('/user/read/:user_id', function(req, res) {
        User.findById(req.params.user_id, function (err, user) {
        	if (err) 
        		res.send(err);

        	res.json(user);
        })
    });

    app.get('/user/get/:user_email', function(req, res) {
    	User.find({ 'email' : req.params.user_email }, function (err, user) {
        	if (err) 
        		res.send(err);

        	res.json(user);
        })
    });

    app.get('/user/team/:user_team', function(req, res) {
        User.find({ 'idTeam' : req.params.user_team }, function (err, user) {
            if (err) 
                res.send(err);

            res.json(user);
        })
    });

    app.get('/user/delete/:user_id', function(req, res) {
        User.findByIdAndRemove(req.params.user_id, function (err) {
        	if (err) 
        		res.send(err);

        	res.send('success');
        })
    });
}