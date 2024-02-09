import os
from flask import *
from werkzeug.utils import secure_filename

from src.dbconnectionnew import *

app=Flask(__name__)


@app.route('/login',methods=['post'])
def login():
    print(request.form)
    username=request.form['username']
    password=request.form['password']
    qry="select * from login where username=%s and password=%s"
    val=(username,password)
    s=selectone(qry,val)

    print(s)

    if s is None:
        return jsonify({'task':'invalid'})
    else:
        id=s['lid']
        type = s['type']

        return jsonify({'task':'valid',"id" : id,"type":type })



@app.route('/driverreg',methods=['post'])
def driverreg():
    fname = request.form['fname']
    lname = request.form['lname']
    gender = request.form['gender']
    place = request.form['place']
    post = request.form['post']
    pin = request.form['pin']
    contact = request.form['contact']
    email = request.form['email']
    proof = request.form['proof']
    uname=request.form['uname']
    psw=request.form['psw']
    qry="INSERT INTO `login` VALUES(NULL,%s,%s,'pending')"
    val=(uname,psw)
    id=iud(qry,val)

    qry1="INSERT INTO `cab` VALUES(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    val1=(str(id),fname,lname,gender,place,post,pin,contact,email,proof)
    iud(qry1, val1)
    return jsonify(task="success")




@app.route('/view_booking',methods=['post'])
def view_booking():
    lid=request.form['lid']
    qry="SELECT `user`.*,`cab_booking`.* FROM `user` JOIN `cab_booking` ON `user`.lid=`cab_booking`.lid WHERE `cab_booking`.`c_id`=%s AND status='pending'"
    res=selectall2(qry,lid)
    print(res)
    return jsonify(res)

@app.route('/cabbooking_status',methods=['post'])
def cabbooking_status():
    lid=request.form['lid']
    qry="SELECT `cab_booking`.*,`cab`.`Firstname`,`cab`.`lastname` FROM `cab` JOIN `cab_booking` ON `cab_booking`.`c_id`=`cab`.`lid`   WHERE `cab_booking`.lid=%s"
    res=selectall2(qry,lid)
    print(res)
    return jsonify(res)

# @app.route('/update_status_ofmyride',methods=['post'])
# def update_status_ofmyride():
#     lid=request.form['lid']
#     qry=""






@app.route('/acceptcabbooking',methods=['post'])
def acceptcabbooking():
    bid = request.form['bid']
    qry="UPDATE `cab_booking` SET `status`='accepted' WHERE `b_id`=%s"
    iud(qry,bid)
    return jsonify(task="success")

@app.route('/rejectcabbooking',methods=['post'])
def rejectcabbooking():
    bid = request.form['bid']
    qry="UPDATE `cab_booking` SET `status`='rejected' WHERE `b_id`=%s"
    iud(qry,bid)
    return jsonify(task="success")


@app.route('/sendcomplaint',methods=['post'])
def sendcomplaint():
    lid = request.form['lid']
    complaint = request.form['complaint']
    qry="INSERT INTO `complaint` VALUES(NULL,%s,%s,'pending',CURDATE())"
    val=(lid,complaint)
    iud(qry,val)
    return jsonify(task="valid")


@app.route('/viewreply',methods=['post'])
def viewreply():
    lid=request.form['lid']
    qry="SELECT *  FROM `complaint` WHERE `lid`=%s"
    res=selectall2(qry,lid)
    return jsonify(res)

@app.route('/view_feedback',methods=['post'])
def view_feedback():
    qry="SELECT feedback.*,user.* FROM feedback JOIN `user` ON feedback.u_id=user.lid"
    res=selectall(qry)
    print(res)
    return jsonify(res)


@app.route('/view_workshop',methods=['post'])
def view_workshop():
    qry="SELECT * FROM `workshop`"
    res=selectall(qry)
    return jsonify(res)




@app.route('/view_service',methods=['post'])
def view_service():
    w_id=request.form['wid']
    print(w_id)
    qry="SELECT * FROM `service` WHERE `w_id`=%s"
    res=selectall2(qry,w_id)
    print(res)
    return jsonify(res)





@app.route('/book_service',methods=['post'])
def book_service():
    lid=request.form['lid']
    service_id=request.form['sid']
    qry="INSERT INTO `w_booking` VALUES(NULL,%s,%s,curdate(),curtime(),'pending')"
    val=(lid,service_id)
    iud(qry,val)
    return jsonify(task="success")


@app.route('/view_status',methods=['post'])
def view_status():
    lid=request.form['lid']
    qry="SELECT `w_booking`.*,`service`.* FROM `service` JOIN `w_booking` ON `service`.`s_id`=`w_booking`.`service_id` WHERE `w_booking`.`lid`=%s"
    res=selectall2(qry,lid)
    print(res)
    return jsonify(res)

@app.route('/viewworkshop',methods=['post'])
def viewworkshop():
    qry="SELECT * FROM workshop"
    res=selectall(qry)
    print(res)
    return jsonify(res)

@app.route('/add_rating',methods=['post'])
def add_rating():
    lid = request.form['lid']
    w_id = request.form['w_id']
    rating=request.form['rating']
    qry="INSERT INTO `rating` VALUES(NULL,%s,%s,%s,CURDATE())"
    val = (lid, w_id,rating)
    iud(qry, val)
    return jsonify(task="success")






@app.route('/viewnearestworskshop',methods=['POST'])
def viewnearestworskshop():
    print(request.form)
    lattitude=request.form['lattitude']
    longitude=request.form['longitude']
    print(lattitude,longitude,"===========================")
    qry="SELECT `workshop`.*, (3959 * ACOS ( COS ( RADIANS('"+lattitude+"') ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS('"+longitude+"') ) + SIN ( RADIANS('"+lattitude+"') ) * SIN( RADIANS( latitude ) ))) AS user_distance FROM `workshop` HAVING user_distance  < 31.068 "
    res=selectall(qry)

    return jsonify(res)






@app.route('/addlocation', methods=['post'])
def addlocation():
    lid=request.form['lid']
    lat=request.form['lat']
    lon=request.form['lon']
    qry="SELECT * FROM `location` WHERE `lid`=%s"
    res=selectone(qry,lid)
    if res is None:
        qry="INSERT INTO `location` VALUES(NULL,%s,%s,%s)"
        val=(lid,lat,lon)
        iud(qry,val)
    else:
        qry="UPDATE `location` SET `latitude`=%s,`longitude`=%s WHERE `lid`=%s"
        val=(lat,lon,lid)
        iud(qry,val)
    return jsonify({'task': 'valid'})








# <<=======================================user=====================>>==<<==================================================>>==========<>===========









@app.route('/book_cab',methods=['post'])
def book_cab():
    start=request.form['start']
    end=request.form['end']
    time=request.form['time']
    lid=request.form['lid']
    cid=request.form['cid']
    qry="INSERT INTO `cab_booking` VALUES(NULL,%s,%s,%s,%s,CURDATE(),%s,'pending')"
    val=(cid,lid,start,end,time)
    iud(qry,val)
    return jsonify({'task': 'success'})










@app.route('/sendfeed',methods=['post'])
def sendfeed():
    lid=request.form['lid']
    feed=request.form['feedback']
    qry="insert into feedback values(null,%s,curdate(),%s)"
    val=(lid,feed)
    iud(qry,val)
    return jsonify({'task': 'valid'})


@app.route('/share_ride',methods=['post'])
def share_ride():
    start = request.form['from']
    end = request.form['to']
    date = request.form['date']
    time = request.form['time']
    lid = request.form['lid']
    qry = "INSERT INTO `ride_info` VALUES(NULL,%s,%s,%s,%s,'pending',%s)"
    val = (lid, start, end,date,time)
    iud(qry, val)
    return jsonify({'task':'valid'})


@app.route('/view_ride',methods=['post'])
def view_ride():
    lid=request.form['lid']
    qry="SELECT ride_info.*,user.*  FROM ride_info JOIN USER ON ride_info.u_id=user.lid WHERE ride_info.u_id!=%s"
    res=selectall2(qry,lid)
    return jsonify(res)

@app.route('/view_ridereq',methods=['post'])
def view_ridereq():
    lid=request.form['lid']
    qry="    SELECT `user`.`firstname`,`lastname`,`request`.`date`,request.req_id,`ride_info`.`from`,`ride_info`.`to` FROM `ride_info` JOIN `request` ON `request`.`rid`=`ride_info`.`r_id` JOIN `user` ON `request`.`u_id`=`user`.`lid` WHERE `ride_info`.`u_id`=%s AND request.status='pending'"
    res=selectall2(qry,lid)
    print(res)
    return jsonify(res)



@app.route('/view_request',methods=['post'])
def view_request():
    lid=request.form['lid']
    qry="SELECT `request`.*,`ride_info`.*,`user`.*  FROM `request` JOIN `ride_info` ON  `request`.`rid`=`ride_info`.r_id JOIN `user` ON user.lid=request.u_id WHERE `ride_info`.u_id=%s AND request.status='pending'"
    res=selectall2(qry,lid)
    return jsonify(res)

@app.route('/view_riderequeststatus',methods=['post'])
def view_riderequeststatus():
    lid=request.form['lid']
    qry="    SELECT `request`.*,`ride_info`.`date` AS rdate ,`ride_info`.*,`user`.*  FROM `request` JOIN `ride_info` ON  `request`.`rid`=`ride_info`.r_id JOIN `user` ON user.lid=request.u_id WHERE `ride_info`.u_id=%s"
    res=selectall2(qry,lid)
    return jsonify(res)

@app.route('/accept',methods=['post'])
def accept():
    lid = request.form['rid']
    qry="UPDATE `request` SET `status`='accepted' WHERE req_id=%s"
    iud(qry,lid)
    return jsonify({'task':'valid'})

@app.route('/reject', methods=['post'])
def reject():
        lid = request.form['rid']
        qry = "UPDATE `request` SET `status`='rejected' WHERE req_id=%s"
        iud(qry,lid)
        return jsonify({'task': 'valid'})


@app.route('/update_status',methods=['post'])
def update_status():
    rid=request.form['rid']
    qry="UPDATE `ride_info` SET STATUS='completed' WHERE r_id=%s"
    iud(qry,rid)
    return jsonify(task="success")

@app.route('/viewmyride',methods=['post'])
def viewmyride():
    lid=request.form['lid']
    qry="SELECT * FROM `ride_info` WHERE u_id=%s"
    res=selectall2(qry,lid)
    print(res)
    return jsonify(res)

@app.route('/viewsharedride',methods=['post'])
def viewsharedride():
    lid=request.form['lid']
    qry="SELECT * FROM `ride_info` WHERE `status`='pending' AND `u_id`!=%s"
    res=selectall2(qry,lid)
    print(res)
    return jsonify(res)


@app.route('/viewnearestcab',methods=['POST'])
def viewnearestcab():
    print(request.form)
    lattitude=request.form['lattitude']
    longitude=request.form['longitude']
    print(lattitude,longitude,"===========================")
    qry="SELECT `cab`.*,location.* , (3959 * ACOS ( COS ( RADIANS('"+lattitude+"') ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS('"+longitude+"') ) + SIN ( RADIANS('"+lattitude+"') ) * SIN( RADIANS( latitude ) ))) AS user_distance FROM `cab` JOIN location ON cab.lid=location.lid  HAVING user_distance  < 31.068 "
    res=selectall(qry)
    print(res)
    return jsonify(res)



@app.route('/userreg', methods=['post'])
def userreg():
        fname = request.form['fname']
        lname = request.form['lname']
        phone = request.form['phone']
        gender = request.form['gender']
        email = request.form['email']
        place=request.form['place']
        post=request.form['post']
        pin=request.form['pin']
        image = request.files['file']
        fname1 = secure_filename(image.filename)
        image.save(os.path.join('static/uploads', fname1))
        uname=request.form['username']
        Pwrd=request.form['password']
        qry = "INSERT INTO `login` VALUES(NULL,%s,%s,'user')"
        val = (uname,Pwrd)
        s = iud(qry, val)
        qry="INSERT INTO `user`VALUES(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        val=(str(s),fname,lname,gender,place,post,pin,phone,email,fname1)
        iud(qry,val)
        return jsonify({'task': 'success'})

@app.route('/updatestatus', methods=['post'])
def updatestatus():
    r_id = request.form['r_id']
    qry = "UPDATE `ride_info` SET `status`='completed' WHERE r_id= %s"
    val = (r_id)
    iud(qry, val)
    return jsonify({'task': 'success'})



@app.route('/cabreg', methods=['post'])
def cabreg():
        fname = request.form['fname']
        lname = request.form['lname']
        phone = request.form['phone']
        gender = request.form['gender']
        email = request.form['email']
        place=request.form['place']
        post=request.form['post']
        pin=request.form['pin']
        image = request.files['file']
        fname1 = secure_filename(image.filename)
        image.save(os.path.join('static/uploads', fname1))
        uname=request.form['username']
        Pwrd=request.form['password']
        qry = "INSERT INTO `login` VALUES(NULL,%s,%s,'pending')"
        val = (uname,Pwrd)
        s = iud(qry, val)
        qry="INSERT INTO `cab` VALUES(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        val=(str(s),fname,lname,gender,place,post,pin,phone,email,fname1)
        iud(qry,val)
        return jsonify({'task': 'success'})


@app.route('/viewrideinfo',methods=['post'])
def viewrideinfo():
    lid=request.form['lid']
    qry="SELECT * FROM `ride_info` WHERE u_id!=%s"
    res=selectall2(qry,lid)
    return jsonify(res)

@app.route('/sendreq',methods=['post'])
def sendreq():
    lid=request.form['lid']
    rid=request.form['rid']
    qry="INSERT INTO `request` VALUES(NULL,%s,%s,CURDATE(),'pending')"
    val=(lid,rid)
    iud(qry,val)
    return jsonify({'task': 'success'})


app.run(port='5000',host='0.0.0.0')

