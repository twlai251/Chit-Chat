package com.example.chitchat

class User {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var uid: String? = null

    constructor() {

    }

    constructor(firstName: String?, lastName: String?, email: String?, uid: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.uid = uid
    }

}