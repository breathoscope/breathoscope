# Room Persistence Library - The Abridged Version

## Entities

A class annotated with @Entity corresponds to a table in a database. Fields correspond to columns and can be annotated as @PrimaryKey, @NonNull etc.We can also set the column names to be different to its identifier in the class using @ColumnInfo(name="desired_name"). When creating an entity make public getters and setters for all fields otherwise Room won't work.

## DAO

Stands for Data Access Object - its the class in which we write our queries. We can have more than one (e.g. one per table) but we'll use one for now and refactor if things get too complex.

We can do create, read, update and delete through the DAO - best to look at the docs to figure out the best way to construct your query.

## Database

The AppRoomDatabase class has one function to return the database object (using singleton pattern) and public access to its DAO. These probably won't need to be touched *however* the @Database annotation needs to be updated whenever we need to add a new entity/table. Just add the entity's .class file as shown.

## Examples

Some database usage examples are given in DbExamples.java. This class is just to demo how to use the DAO, don't use it as a helper class.

## Lastly

I've enabled queries on main thread for the database. This is a no no for production so only use it for testing, and don't push code with queries on main thread.
