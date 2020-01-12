// imports mongoose
const mongoose = require('mongoose');
// schema is a JSON object
// - defines shape and content of documents in collection
const Schema   = mongoose.Schema;

const taskSchema = new Schema({
  task: { type: String },
});

module.exports = mongoose.model('Task', taskSchema);