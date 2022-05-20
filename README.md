# wa2-g12-traveler-service
Repository for the Lab 4 assignment of the Web Applications II course at Polytechnic University of Turin (academic year 2021-2022).

## Group 12 members:
| Student ID | Surname | Name |
| --- | --- | --- |
| s286154 | Ballario | Marco |
| s277873 | Galazzo | Francesco |
| s276086 | Tangredi | Giovanni |
| s292522 | Ubertini | Pietro |

## Usage

The Login service can be found in the repository ```wa2-g12-user-registration``` on the branch ```lab-4```:
- ```cd wa2-g12-user-registration```
- ```git checkout lab-4```

When the container is started the SQL initialization script in the init folder is executed to create the two databases:
- ```login_db``` 
- ```traveler_db```

To start the project:

1. Move inside the project root: ```cd wa1-g12-traveler-service```
2. Build the custom image: ```docker build -t my-postgres-image .```
3. Create the persistent volume: ```docker volume create my-postgres-volume```
4. Instantiate the container: ```docker run --name my-postgres-container -e POSTGRES_PASSWORD=postgres -d -p 54320:5432 -v my-postgres-volume:/var/lib/postgres/data my-postgres-image```

To remove the project:

1. Stop the container: ```docker stop my-postgres-container```
2. Remove the container: ```docker rm my-postgres-container```
3. Remove the volume: ```docker volume rm my-postgres-volume```
4. Remove the image: ```docker image rm my-postgres-image```
