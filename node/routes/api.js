// importing our model in the models/task.js file
// it's a Schema with the task structure
const Task  = require('../models/task');
// express is for API
const express = require('express');

// creating a router for API calls
const router = express.Router();

// adding a event handler to a post request to a new path
router.post('/new',
  // reqest is the request (with body being the data
  // variables of the request)

  // TODO: Find out what res means
  // hypothesis: it is the return value (what it displays
  // on the result webpage (res may stand for result)

  // status code of 500 represents server error,
  // status code of 200 represents OK
  (req, res) => {
  Task.create({
    task: req.body.task,
  }, (cerr, task) => {
    if (err) {
      // announce an error during task creation
      console.log('CREATE Error: ' + err);
      res.status(500).send('Error');
    } else {
      res.status(200).json(task);
    }
  });
});

router.route('/:id').delete((req, res) => {
  Task.findById(req.params.id, (err, task) => {
    if (err) {
      console.log('DELETE Error: ' + err);
      res.status(500).send('Error');
    } else if (task) {
      // delete the task and remove the json of the task
      task.remove( () => {
        res.status(200).json(task);
      });
    } else {
      // 404 not found error (the task to delete could
      // not be found
      res.status(404).send('Not found');
    }
  });
});

// exporing our router module for other modules to use
module.exports = router;


