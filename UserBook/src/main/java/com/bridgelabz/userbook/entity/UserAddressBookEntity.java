package com.bridgelabz.userbook.entity;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;
@Entity
@Data
@Table(name="Book")
public class UserAddressBookEntity 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long bookid;
   @NotBlank(message="Book name is mandatory")
    private String title;
   private LocalDateTime purchasedTime;
    private long userId;
}
