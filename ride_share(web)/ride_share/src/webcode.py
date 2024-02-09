from flask import *

from src.dbconnectionnew import *

app=Flask(__name__)
app.secret_key="jhgf"

@app.route('/')
def login():
    return render_template("login_index.html")

@app.route('/login_post',methods=['post','get'])
def login_post():
    print(request.form)
    uname=request.form['textfield']
    password=request.form['textfield2']
    qry="SELECT * FROM `login` WHERE `username`=%s AND `password`=%s"
    val=(uname,password)
    res=selectone(qry,val)
    if res is None:
        return '''<script>alert("Invalid username or password");window.location='/'</script>'''
    elif res['type']=='admin':
        return '''<script>alert("Welcome admin");window.location='/adminhome'</script>'''
    elif res['type']=='workshop':
        session['lid']=res['lid']
        return '''<script>alert("Welcome workshop");window.location='/workshophome'</script>'''
    else:
        return '''<script>alert("Invalid!!!");window.location='/'</script>'''

#+++++++++++++++++++++++++++++++++++++++++++++ADMIN+++++++++++++++++++++++++++++++++++++++++++++++
@app.route('/adminhome')
def adminhome():
    return render_template('admin/admin_index.html')

@app.route('/view_workshop')
def view_workshop():
    qry = "SELECT * FROM `workshop` "
    res  = selectall(qry)
    return render_template('admin/AddandManageWorkshop.html', val = res)

@app.route('/add_workshop')
def add_workshop():
    return render_template('admin/add_workshop.html')

@app.route('/add_workshop_post',methods=['post'])
def add_workshop_post():
    name = request.form['textfield1']
    place = request.form['textfield2']
    post = request.form['textfield3']
    pin = request.form['textfield4']
    contact = request.form['textfield5']
    email = request.form['textfield6']
    uname = request.form['textfield7']
    password = request.form['textfield8']

    lati = request.form['lati']
    longi = request.form['longi']

    q=" INSERT INTO `login` VALUES (NULL,%s,%s,'workshop')"
    val=(uname,password)
    res=iud(q,val)

    qry=" INSERT INTO `workshop` VALUES(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    val1=(str(res),name,place,post,pin,contact,email,lati,longi)
    iud(qry,val1)

    return '''<script>alert("Added successfully");window.location='/view_workshop'</script>'''


@app.route('/add_service_post',methods=['post'])
def add_service_post():
    service = request.form['textfield']
    details = request.form['textfield2']
    cost = request.form['textfield3']

    q = " INSERT INTO `service` VALUES (NULL,%s,%s,%s,%s)"
    val=(session['lid'],service,details,cost)
    iud(q,val)
    return '''<script>alert("Added successfully");window.location='/Manage_services'</script>'''

@app.route('/send_complaint',methods=['post'])
def send_complaint():
    complaint = request.form['textarea']
    q = " INSERT INTO `complaint` VALUES (NULL,%s,%s,'pending',curdate())"
    val = (session['lid'], complaint)
    iud(q, val)
    return '''<script>alert("complaint send successfully");window.location='/View_Reply'</script>'''

@app.route('/edit_workshop_post',methods=['post'])
def edit_workshop_post():
    name = request.form['textfield1']
    place = request.form['textfield2']
    post = request.form['textfield3']
    pin = request.form['textfield4']
    contact = request.form['textfield5']
    email = request.form['textfield6']
    qry="UPDATE `workshop` SET `name`=%s,`place`=%s,`post`=%s,`pin`=%s,`contact`=%s,`email`=%s WHERE lid=%s"
    val=(name,place,post,pin,contact,email,session['wid'])
    iud(qry,val)
    return '''<script>alert("updated successfully");window.location='/view_workshop'</script>'''


@app.route('/complaint1',methods=['post'])
def complaint1():
    type = request.form['select']
    if type == 'user':
        qry="       SELECT `user`.`firstname` AS `name`,`complaint`.* FROM `complaint` JOIN `user` ON `user`.`lid`=`complaint`.`lid` WHERE `complaint`.`reply`='pending'"
        # qry = "SELECT `user`.`firstname` as name,`complaint`.* FROM `complaint` JOIN 'user' ON `user`.`lid`=`complaint`.`lid` WHERE `complaint`.`reply`='pending'"
        res = selectall(qry)
        return render_template('admin/complaint.html',val = res)
    else:
        qry = "SELECT * FROM `complaint` JOIN `workshop` ON `workshop`.`lid`=`complaint`.`lid` WHERE `complaint`.`reply`='pending'"
        res = selectall(qry)
        return render_template('admin/complaint.html',val = res)


@app.route('/complaint')
def complaint():
    return render_template('admin/complaint.html')


@app.route('/send_reply')
def send_reply():
    id=request.args.get('id')
    session['cid']=id
    return render_template('admin/send_reply.html')


@app.route('/send_reply1',methods=['post'])
def send_reply1():
    rply=request.form['textarea']
    qry="UPDATE `complaint` SET `reply`=%s WHERE `c_id`=%s"
    val=rply,session['cid']
    iud(qry,val)
    return '''<script>alert(" reply send successfully");window.location="/complaint"</script>'''

@app.route('/verify_cab')
def verify_cab():
    qry = "SELECT * FROM `cab` JOIN `login` ON `cab`.`lid`=`login`.`lid` WHERE `type`='pending'"
    res = selectall(qry)
    return render_template('admin/Verify_cab.html', val = res)

@app.route('/deleteworkshop')
def deleteworkshop():
    lid=request.args.get('id')
    q="DELETE FROM `workshop` WHERE `lid`=%s"
    iud(q,lid)
    qry="DELETE FROM `login` WHERE `lid`=%s"
    iud(qry,lid)
    return '''<script>alert(" deleted successfully");window.location="view_workshop"</script>'''




@app.route('/editworkshop')
def editworkshop():
    lid=request.args.get('id')
    session['wid']=lid
    q = "SELECT *FROM `workshop` WHERE `lid`=%s"
    res=selectone(q,lid)
    return render_template("admin/edit_workshop.html",val=res)


@app.route('/approve_cab')
def approve_cab():
    id = request.args.get('id')
    qry = "UPDATE `login` SET `type`='cab' WHERE `lid`=%s"
    iud(qry,id)
    return '''<script>alert("success");window.location="verify_cab"</script>'''

@app.route('/reject_cab')
def reject_cab():
    id = request.args.get('id')
    qry = "UPDATE `login` SET `type`='cab' WHERE `lid`=%s"
    iud(qry,id)
    return '''<script>alert("success");window.location="verify_cab"</script>'''

@app.route('/view_feedback')
def view_feedback():
    qry = "SELECT`user`.`firstname`, `lastname`, `feedback`. * FROM`feedback`JOIN`user`ON`feedback`.`u_id` = `user`.`lid`"
    res = selectall(qry)
    return render_template('admin/view_feedback.html',val = res)

@app.route('/view_user')
def view_user():
    qry = "SELECT * FROM  `user`"
    res = selectall(qry)
    return render_template('admin/view_user.html', val = res)

#===================================WORKSHOP============================================================

@app.route('/workshophome')
def workshophome():
    return render_template('workshop/workshop_index.html')

@app.route('/Add_Service')
def Add_Service():
    return render_template('workshop/Add services.html')

@app.route('/Manage_services')
def Manage_Services():
    qry="SELECT * FROM `service` WHERE`w_id`=%s"
    res=selectall2(qry,session['lid'])
    return render_template('workshop/Manage services.html',val=res)

@app.route('/deleteservices')
def deleteservices():
    id=request.args.get('id')
    q="DELETE FROM `service` WHERE `s_id`=%s"
    iud(q,id)
    return '''<script>alert(" deleted successfully");window.location="Manage_services"</script>'''

@app.route('/editservice')
def editservice():
    lid=request.args.get('id')
    session['s_id']=lid
    q = "SELECT *FROM `service` WHERE `s_id`=%s"
    res=selectone(q,lid)
    print(res)
    return render_template("workshop/edit services.html",val=res)

@app.route('/edit_services_post',methods=['post'])
def edit_services_post():
    Service = request.form['textfield']
    Details = request.form['textfield2']
    cost = request.form['textfield3']
    qry="UPDATE `service` SET `service`=%s,`details`=%s,`cost`=%s WHERE s_id=%s"
    val=(Service,Details,cost,session['s_id'])
    iud(qry,val)
    return '''<script>alert("updated successfully");window.location='/Manage_services'</script>'''

@app.route('/Send_Complaint')
def Send_Complaint():
    return render_template('workshop/sendcomplaint.html')

@app.route('/View_booking_details_and_update')
def View_booking_details_and_update():
    return render_template('workshop/viewbookingdetailsandupdate.html')
@app.route('/View_booking_details_and_update1',methods=['post'])
def View_booking_details_and_update1():
    type=request.form['select']
    if type =='user':
        qry="SELECT  * FROM `service`JOIN `w_booking` ON `w_booking`.`service_id`=`service`.`s_id` JOIN `user`ON `user`.`lid`=`w_booking`.`lid` WHERE `service`.`w_id`= %s"
        res=selectall2(qry,session['lid'])
        return render_template('workshop/viewbookingdetailsandupdate.html',val=res)
    else:
        qry1="SELECT  `cab`.`Firstname`AS firstname,`cab`.`lastname`,`service`.*, `w_booking`.*FROM `service`JOIN `w_booking` ON `w_booking`.`service_id`=`service`.`s_id` JOIN `cab`ON `cab`.`lid`=`w_booking`.`lid` WHERE `service`.`w_id`= %s"
        res = selectall2(qry1, session['lid'])
        return render_template('workshop/viewbookingdetailsandupdate.html',val=res)


@app.route('/acceptbooking')
def acceptbooking():
    id=request.args.get('id')
    qry="UPDATE `w_booking` SET `status`='accepted' WHERE b_id=%s"
    iud(qry,id)
    return '''<script>alert("updated successfully");window.location='/View_booking_details_and_update'</script>'''

@app.route('/rejectbooking')
def rejectbooking():
    id=request.args.get('id')

    qry="UPDATE `w_booking` SET `status`='rejected' WHERE b_id=%s"
    iud(qry,id)
    return '''<script>alert("updated successfully");window.location='/View_booking_details_and_update'</script>'''


@app.route('/View_Rating')
def View_Rating():
    qry="    SELECT `rating`.`rating`,`date`,`cab`.`Firstname`,`lastname` FROM `rating` JOIN `cab` ON `rating`.lid=`cab`.lid  WHERE `rating`.`w_id`=%s"
    res=selectall2(qry,session['lid'])
    return render_template('workshop/viewrating.html',val=res)

@app.route('/View_Reply')
def View_Reply():
    # qry="SELECT`user`.`firstname`, `user`.`lastname`, `complaint`. * FROM`complaint`JOIN`user`ON`complaint`.lid = `user`.lid"
    qry = "SELECT`workshop`.`name`,`complaint`. * FROM`complaint`JOIN`workshop`ON`complaint`.lid = `workshop`.lid"
    res=selectall(qry)
    return render_template('workshop/viewreply.html',val=res)

app.run(debug=True)