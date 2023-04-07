let express=require('express');

let app=express();
const port = process.env.PORT || 3000;


app.get('/',(req,res)=>{
   res.send("Hello folks")
})




app.listen(port,()=>{console.log(`server is running at port 3000`);})