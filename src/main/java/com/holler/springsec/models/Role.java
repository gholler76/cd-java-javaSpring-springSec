package com.holler.springsec.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {
	 @Id
	 @GeneratedValue (strategy = GenerationType.IDENTITY)
	 private Long id;
	 private String name;
	 @Column(updatable=false)
	 private Date createdAt;
	 private Date updatedAt;
	 @ManyToMany(mappedBy = "roles")
	 private List<User> users;
	 
	 public Role() {
	 }
	 
	 public Long getId() {
	     return id;
	 }
	 public void setId(Long id) {
	     this.id = id;
	 }
	 public String getName() {
	     return name;
	 }
	 public void setName(String name) {
	     this.name = name;
	 }
	 public List<User> getUsers() {
	     return users;
	 }
	 public void setUsers(List<User> users) {
	     this.users = users;
	 
	 }
	 
	 @PrePersist
	 protected void onCreate(){
	     this.createdAt = new Date();
	 }
	 @PreUpdate
	 protected void onUpdate(){
	     this.updatedAt = new Date();
	 }
}