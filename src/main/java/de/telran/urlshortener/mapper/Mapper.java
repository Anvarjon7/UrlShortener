package de.telran.urlshortener.mapper;

import java.util.Set;

public interface Mapper<Entity, Dto> {

    Dto toDto(Entity entity);
    Set<Dto> toDtoSet(Set<Entity> entitySet);

    Entity toEntity(Dto dto);

    Set<Entity> toEntitySet(Set<Dto> dtoSet);
}
