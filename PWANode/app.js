let express = require('express');
let cors = require('cors')
let app = express();
app.use(cors());

app.use(express.static('www'));

let server = app.listen(8081, function () {

    let host = server.address().address;
    let port = server.address().port;

    console.log('Express app listening at http://%s:%s', host, port);

});