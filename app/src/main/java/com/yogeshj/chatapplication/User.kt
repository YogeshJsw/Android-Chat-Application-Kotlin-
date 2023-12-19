package com.yogeshj.chatapplication

//Since firebase requires an empty constructor hence we are not using data class
class User {
    var uid:String?=null
    var name:String?=null
    var email:String?=null

    constructor()

    constructor(uid:String,name:String,email: String){
        this.uid=uid
        this.name=name
        this.email=email
    }
}