
package com.bank.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="accounts")
public class Account {
  @Id
  public String id;
  public String user;
  public double balance;
}
