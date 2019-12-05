
--    public static void addCustomer(DBProject esql){
-- 	  // Given customer details add the customer in the DB 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end addCustomer

-- INSERT INTO Customer
--     (customerID, fName, lName, Address, phNo, DOB, gender)
-- VALUES
--     ('12344', 'lucas', 'song', '12f lol', '49999', '2019-12-05', 'Male');

--    public static void addRoom(DBProject esql){
-- 	  // Given room details add the room in the DB
--       // Your code goes here.
--       // ...
--       // ...
--    }//end addRoom

-- INSERT INTO Room
--     (hotelID, roomNo, roomType)
-- VALUES
--     (hotelID, roomNo, roomType);

-- --    public static void addMaintenanceCompany(DBProject esql){
-- --       // Given maintenance Company details add the maintenance company in the DB
-- --       // ...
-- --       // ...
-- --    }//end addMaintenanceCompany
-- INSERT INTO MaintenanceCompany
--     (cmpID, name, address, isCertified)
-- VALUES
--     (cmpID, name, address, isCertified);

-- --    public static void addRepair(DBProject esql){
-- -- 	  // Given repair details add repair in the DB
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end addRepair
-- INSERT INTO Repair
--     (rID, hotelID, roomNo, mCompany, repairDate, description, repairType)
-- VALUES
--     (rID, hotelID, roomNo, mCompany, repairDate, description, repairType);

-- --    public static void bookRoom(DBProject esql){
-- -- 	  // Given hotelID, roomNo and customer Name create a booking in the DB 
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end bookRoom
-- INSERT INTO Booking
--     ( bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price)
-- VALUES
--     ( bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price);

-- --    public static void assignHouseCleaningToRoom(DBProject esql){
-- -- 	  // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end assignHouseCleaningToRoom
-- INSERT INTO Assigned
--     (asgID, staffId, hotelID, roomNo)
-- VALUES
--     (asgID, staffId, hotelID, roomNo);

-- --    public static void repairRequest(DBProject esql){
-- -- 	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end repairRequest
-- INSERT INTO Repair
--     (rID, hotelID, roomNo, mCompany, repairDate, description, repairType)
-- VALUES
--     (rID, hotelID, roomNo, mCompany, repairDate, description, repairType);

-- --    public static void numberOfAvailableRooms(DBProject esql){
-- -- 	  // Given a hotelID, get the count of rooms available 
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end numberOfAvailableRooms

-- TESTED --

-- SELECT COUNT(*)
-- FROM Room R
-- WHERE R.hotelID = '5' AND R.roomNo NOT IN (SELECT B.roomNo
-- FROM Booking B
-- WHERE R.hotelID = B.hotelID AND B.hotelID = '5');

-- --    public static void numberOfBookedRooms(DBProject esql){
-- -- 	  // Given a hotelID, get the count of rooms booked
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end numberOfBookedRooms
-- hotelID = USER_ENTERED

-- TESTED --

SELECT COUNT(*)
FROM Booking B
WHERE B.hotelID = '5';

-- --    public static void listHotelRoomBookingsForAWeek(DBProject esql){
-- -- 	  // Given a hotelID, date - list all the rooms available for a week(including the input date) 
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end listHotelRoomBookingsForAWeek
-- ** DATE **

-- TESTED, need to substitute date and hotel id

SELECT R.roomNo, R.roomType
FROM Room R
WHERE R.hotelID = '1' AND R.roomNo NOT IN (SELECT B.roomNo
    FROM Booking B
    WHERE R.hotelID = B.hotelID AND B.bookingDate >= '12/8/2016' AND B.bookingDate <= '12/10/2016');

-- --    public static void topKHighestRoomPriceForADateRange(DBProject esql){
-- -- 	  // List Top K Rooms with the highest price for a given date range
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end topKHighestRoomPriceForADateRange

-- TESTED, need to substitute date and 'k'

SELECT B.price, R.roomNo, R.roomType
FROM Booking B, Room R
WHERE B.roomNo = R.roomNo AND B.bookingDate >= '12/8/2000' AND B.bookingDate <= '1/10/2015'
ORDER BY B.price DESC
LIMIT 5;

-- --    public static void topKHighestPriceBookingsForACustomer(DBProject esql){
-- -- 	  // Given a customer Name, List Top K highest booking price for a customer 
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end topKHighestPriceBookingsForACustomer
-- k = user_defined

-- TESTED, need to substitute fname, lname and 'k'

SELECT B.price
FROM Booking B, Customer C
WHERE 'wbvu' = C.fname AND 'lroe' = C.lname AND B.customer = C.customerID 
ORDER BY B.price DESC
LIMIT 5;

-- --    public static void totalCostForCustomer(DBProject esql){
-- -- 	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end totalCostForCustomer

-- TESTED, need to substitute fname, lname, dates, hotel id

-- SELECT sum(B.price)
-- FROM Booking B, Room R, Customer C
-- WHERE B.hotelID = '2' AND B.customer = C.customerID AND C.fname = 'wbvu' AND C.lname = 'lroe' AND B.bookingDate >= '1/1/1000' AND B.bookingDate <= '1/1/2041';

-- --    public static void listRepairsMade(DBProject esql){
-- -- 	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end listRepairsMade
-- compName = USER_ENTERED

-- TESTED, need to substitute maintenance company

-- SELECT R.repairType, R.hotelID, R.roomNo
-- FROM Repair R, MaintenanceCompany M
-- WHERE R.mcompany = M.cmpID AND M.name = 'ylbx';

-- --    public static void topKMaintenanceCompany(DBProject esql){
-- -- 	  // List Top K Maintenance Company Names based on total repair count (descending order)
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end topKMaintenanceCompany

-- TESTED, need to substitute k

-- SELECT M2.name, M.count
-- FROM (SELECT R1.mCompany as id, COUNT(*) as count
--         FROM Repair R1
--         GROUP BY R1.mCompany) as M, MaintenanceCompany M2
-- WHERE M2.cmpID = M.id
-- ORDER BY M.count DESC
-- LIMIT 5;

-- --    public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
-- -- 	  // Given a hotelID, roomNo, get the count of repairs per year
-- --       // Your code goes here.
-- --       // ...
-- --       // ...
-- --    }//end listRepairsMade

-- TESTED, need to substitute room no & hotel ID

SELECT extract(year from R.repairDate) as ryear, count(*)
FROM Repair R
WHERE R.roomNo = '1' AND R.hotelID = '126'
GROUP BY ryear
;