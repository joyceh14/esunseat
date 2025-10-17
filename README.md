# Seat Management System
A full-stack **seat management system** for managing employees and seats within an organization.

Built with:
- **Backend:** Spring Boot (Java 21, Maven)
- **Frontend:** Vue 3 + Vite
- **Database:** MySQL 8

## Prerequisites
Please install following tools first: 
| Tool         | Version                           | Download                                                             |
| ------------ | --------------------------------- | -------------------------------------------------------------------- |
| **Java JDK** | 21 or higher                      | [https://adoptium.net](https://adoptium.net)                         |
| **Maven**    | 3.9 or higher                     | [https://maven.apache.org](https://maven.apache.org)                 |
| **MySQL**    | 8.0 or higher                     | [https://dev.mysql.com/downloads/](https://dev.mysql.com/downloads/) |
| **Node.js**  | 18 or higher (20 LTS recommended) | [https://nodejs.org](https://nodejs.org)                             |
| **npm**      | Included with Node.js             | —                                                                    |

---

## Step 1. Clone Repository

```bash
git clone https://github.com/<your-username>/esunseat.git
cd esunseat
```

## Step 2. Setup Database (MySQL)
### 1. Create database
```sql
CREATE DATABASE seatdb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```
### 2. Import data tables and sample data 
```bash
cd backend/src/main/resources/db

# Import database table structure
mysql -u root -p esunseat < schema.sql

# Import stored procedures (if any)
mysql -u root -p esunseat < seat_procs.sql

# Import sample data (optional)
mysql -u root -p esunseat < sample_data.sql
```

## Step 3. Configure Environment Variables
### 1. Create `.env`
```bash
cd backend/src/main/resources/db
cp .env.example .env
```
### 2. Edit the contents of the `.env` file as follows (adjust according to your environment): 
```bash
DB_HOST=localhost
DB_PORT=3306
DB_NAME=seatdb
DB_USER=root
DB_PASSWORD=YOUR_MySQL_PASSWORD
```

## Step 4. Run Backend
Run the following commands in the backend directory:
```bash
cd backend
./mvnw spring-boot:run      # macOS / Linux
# or
mvnw.cmd spring-boot:run    # Windows
```
If the startup is successful, you will see:
```nginx
Tomcat started on port 8080
Started EsunseatApplication in X.XXX seconds
```
Backend server address: http://localhost:8080

## Step 5. Run Frontend 
### 1. Enter the frontend directory
```bash
cd ../frontend
npm install
```
### 2. Set up the API endpoint
Create `frontend/.env`: 
```bash
VITE_API_BASE_URL=http://localhost:8080
```
### 3. Activate frontend server 
```bash
npm run dev
```
After Vite starts, it will display:
```arduino
  ➜  Local:   http://localhost:5173/
```
Default frontend address: http://localhost:5173

## Step 6. Access the Application 
Open your browser and go to: http://localhost:5173 
You can now start using the EsunSeat system.
