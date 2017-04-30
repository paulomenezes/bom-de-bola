var fs = require('fs');

module.exports = function(app, db) {
	var Post = require('../models/post');

    app.get('/post', function(req, res) {
        Post.find().sort([['date', -1]]).exec(function (err, posts) {
        	if (err) 
        		res.send(err);

        	res.json(posts);
        })
    });

    app.post('/post/create', function(req, res, next) {
        res.send("success");

        if (req.body.data) {
            console.log(req.body);
            var data = JSON.parse(req.body.data);
            var post = new Post(data);
            post.save();
        } else {
            console.log(req.files);
        }

        res.status(200).end();
    });

    app.get('/post/read/:post_id', function(req, res) {
        Post.findById(req.params.post_id, function (err, post) {
        	if (err) 
        		res.send(err);

        	res.json(post);
        })
    });

    app.get('/post/get/:post_email', function(req, res) {
    	Post.find({ 'email' : req.params.post_email }, function (err, post) {
        	if (err) 
        		res.send(err);

        	res.json(post);
        })
    });

    app.get('/post/delete/:post_id', function(req, res) {
        Post.findByIdAndRemove(req.params.post_id, function (err) {
        	if (err) 
        		res.send(err);

        	res.send('success');
        })
    });
}