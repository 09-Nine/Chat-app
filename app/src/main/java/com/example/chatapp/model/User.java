package com.example.chatapp.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
   private String uid;
   private String userName;
   private String email;
   private String imageUri;

   public User() {
   }

   public User(String uid, String userName, String email, String imageUri) {
      this.uid = uid;
      this.userName = userName;
      this.email = email;
      this.imageUri = imageUri;
   }

   public String getUid() {
      return uid;
   }

   public void setUid(String uid) {
      this.uid = uid;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getImageUri() {
      return imageUri;
   }

   public void setImageUri(String imageUri) {
      this.imageUri = imageUri;
   }


}

