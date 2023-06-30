package com.beyazpoliss.emperor.koth.api;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface Storage {

  CompletableFuture<Void> connectAsync(@NotNull final String url);

  CompletableFuture<Optional<Profile>> getOrEmptyAsync(@NotNull final UUID uuid);

  CompletableFuture<Optional<Profile>> saveAsync(@NotNull final UUID uuid);

  @NotNull
  CompletableFuture<Profile> getOrCreateAsync(@NotNull final UUID uuid);

  @NotNull
  CompletableFuture<Profile> saveOrCreateAsync(@NotNull final UUID uuid);

  void close();

}
