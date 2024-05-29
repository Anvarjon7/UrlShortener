package de.telran.urlshortener.mapper;

public interface Mapper<Entity, Dto> {

    Dto toDto(Entity entity);

    Entity toEntity(Dto dto);
}
