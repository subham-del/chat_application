let express=require('express');
let bodyparser = require('body-parser');
let app=express();
const port = process.env.PORT || 3000;


app.get('/',(req,res)=>{
  
   res.send("Hello folks")
})

app.post('/login',bodyparser.urlencoded({extended:false}),(req,res)=>{
  
  res.send("data received")
})



app.listen(port,()=>{console.log(`server is running at port 3000`);})