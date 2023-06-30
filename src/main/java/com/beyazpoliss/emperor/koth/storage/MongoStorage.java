package com.beyazpoliss.emperor.koth.storage;

import com.beyazpoliss.emperor.koth.api.Profile;
import com.beyazpoliss.emperor.koth.api.Storage;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class MongoStorage implements Storage {

  private MongoClient client;
  private MongoDatabase database;
  private final String collection_name = "Profiles";

  @Override
  public CompletableFuture<Void> connectAsync(@NotNull final String url) {
    return CompletableFuture.runAsync(() -> {
      try {
        client = MongoClients.create(url);
        database = client.getDatabase("emperor-koth");
      }catch (Exception e){
        e.printStackTrace();
      }
    });
  }

  @Override
  public void close() {
    client.close();
  }

  @Override
  public CompletableFuture<Optional<Profile>> getOrEmptyAsync(@NotNull UUID uuid) {
    return null;
  }

  @Override
  public CompletableFuture<Optional<Profile>> saveAsync(@NotNull UUID uuid) {
    return null;
  }

  @Override
  public @NotNull CompletableFuture<Profile> getOrCreateAsync(@NotNull UUID uuid) {
    return null;
  }

  @Override
  public @NotNull CompletableFuture<Profile> saveOrCreateAsync(@NotNull UUID uuid) {
    return null;
  }

}
