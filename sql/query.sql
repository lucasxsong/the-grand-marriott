
--    public static void addCustomer(DBProject esql){
-- 	  // Given customer details add the customer in the DB 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end addCustomer

INSERT INTO Customer
    (customerID, fName, lName, Address, phNo, DOB, gender)
VALUES
    (customerID, fName, lName, Address, phNo, DOB, gender);

--    public static void addRoom(DBProject esql){
-- 	  // Given room details add the room in the DB
--       // Your code goes here.
--       // ...
--       // ...
--    }//end addRoom

INSERT INTO Room
    (hotelID, roomNo, roomType)
VALUES
    (hotelID, roomNo, roomType);

--    public static void addMaintenanceCompany(DBProject esql){
--       // Given maintenance Company details add the maintenance company in the DB
--       // ...
--       // ...
--    }//end addMaintenanceCompany
INSERT INTO MaintenanceCompany
    (cmpID, name, address, isCertified)
VALUES
    (cmpID, name, address, isCertified);

--    public static void addRepair(DBProject esql){
-- 	  // Given repair details add repair in the DB
--       // Your code goes here.
--       // ...
--       // ...
--    }//end addRepair
INSERT INTO Repair
    (rID, hotelID, roomNo, mCompany, repairDate, description, repairType)
VALUES
    (rID, hotelID, roomNo, mCompany, repairDate, description, repairType);

--    public static void bookRoom(DBProject esql){
-- 	  // Given hotelID, roomNo and customer Name create a booking in the DB 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end bookRoom
INSERT INTO Booking
    ( bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price)
VALUES
    ( bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price);

--    public static void assignHouseCleaningToRoom(DBProject esql){
-- 	  // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end assignHouseCleaningToRoom
INSERT INTO Assigned
    (asgID, staffId, hotelID, roomNo)
VALUES
    (asgID, staffId, hotelID, roomNo);

--    public static void repairRequest(DBProject esql){
-- 	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
--       // Your code goes here.
--       // ...
--       // ...
--    }//end repairRequest
INSERT INTO Request
    (rID, hotelID, roomNo, mCompany, repairDate, description, repairType)
VALUES
    (rID, hotelID, roomNo, mCompany, repairDate, description, repairType);

--    public static void numberOfAvailableRooms(DBProject esql){
-- 	  // Given a hotelID, get the count of rooms available 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end numberOfAvailableRooms
SELECT COUNT(*)
FROM Room R
WHERE R.roomNo NOT IN (SELECT B.roomNo
FROM Booking B
WHERE R.hotelID = B.hotelID);

--    public static void numberOfBookedRooms(DBProject esql){
-- 	  // Given a hotelID, get the count of rooms booked
--       // Your code goes here.
--       // ...
--       // ...
--    }//end numberOfBookedRooms
hotelID = USER_ENTERED

SELECT COUNT(*)
FROM Booking B
WHERE hotelID = B.hotelID;

--    public static void listHotelRoomBookingsForAWeek(DBProject esql){
-- 	  // Given a hotelID, date - list all the rooms available for a week(including the input date) 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end listHotelRoomBookingsForAWeek
** DATE **

SELECT R.roomNo, R.roomType
FROM Room R
WHERE R.hotelID = hotelID AND R.roomNo NOT IN (SELECT B.roomNo
    FROM Booking B
    WHERE R.hotelID = B.hotelID AND B.bookingDate = date OR B.bookingDate = date1 OR B.bookingDate = date2 OR B.bookingDate = date3 OR B.bookingDate = date4 OR B.bookingDate = date5 OR B.bookingDate = date6);

--    public static void topKHighestRoomPriceForADateRange(DBProject esql){
-- 	  // List Top K Rooms with the highest price for a given date range
--       // Your code goes here.
--       // ...
--       // ...
--    }//end topKHighestRoomPriceForADateRange

SELECT R.roomNo, R.roomType
FROM Booking B, Room R
LIMIT k
WHERE B.roomNo = R.roomNo AND B.bookingDate >= startDate AND B.bookingDate <= endDate
ORDER BY B.price DESC;

--    public static void topKHighestPriceBookingsForACustomer(DBProject esql){
-- 	  // Given a customer Name, List Top K highest booking price for a customer 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end topKHighestPriceBookingsForACustomer
k = user_defined

SELECT B.price
FROM Booking B, Customer C
LIMIT k
WHERE cname = C.name AND B.customer = C.customerID 
ORDER BY B.price DESC;

--    public static void totalCostForCustomer(DBProject esql){
-- 	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
--       // Your code goes here.
--       // ...
--       // ...
--    }//end totalCostForCustomer
SELECT sum(B.price)
FROM Booking B, Room R, Customer C
WHERE B.hotelID = hotelID AND B.customer = C.customerID AND C.fname = fname AND C.lname = lname AND B.bookingDate >= startDate AND B.bookingDate <= endDate;

--    public static void listRepairsMade(DBProject esql){
-- 	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
--       // Your code goes here.
--       // ...
--       // ...
--    }//end listRepairsMade
compName = USER_ENTERED

SELECT R.repairType, R.hotelID, R.roomNo
FROM Repair R, Maintenance M
WHERE R.mcompany = M.cmpID AND M.name = compName;

--    public static void topKMaintenanceCompany(DBProject esql){
-- 	  // List Top K Maintenance Company Names based on total repair count (descending order)
--       // Your code goes here.
--       // ...
--       // ...
--    }//end topKMaintenanceCompany

SELECT M2.name, M.count
FROM (SELECT R1.id as id, COUNT(*) as count
        FROM Repair R
        GROUP BY R1.mCompany) as M, MaintenanceCompany M2
LIMIT k
WHERE M2.cmpID = M.id
ORDER BY M.count DESC;

--    public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
-- 	  // Given a hotelID, roomNo, get the count of repairs per year
--       // Your code goes here.
--       // ...
--       // ...
--    }//end listRepairsMade

SELECT COUNT(*), EXTRACT(YEAR FROM R.repairDate)
FROM Repair R
GROUP BY EXTRACT(YEAR FROM R.repairDate)
WHERE roomNo = R.roomNo AND R.hotelID = hotelID;