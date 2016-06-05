package demo;

public interface Repository<EntityType, IdType> {

    EntityType findOne(IdType id);

    EntityType save(EntityType entity);

}
