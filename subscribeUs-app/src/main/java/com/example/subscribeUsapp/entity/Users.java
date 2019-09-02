package com.example.subscribeUsapp.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class Users {

	@Id
	private String id;
	private String email;

	public Users(String email) {
		this.email = email;
	}

}
