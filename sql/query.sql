
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
INSERT INTO Assigned(asgID, staffId, hotelID, roomNo)
VALUES Assigned(asgID, staffId, hotelID, roomNo);
   
--    public static void repairRequest(DBProject esql){
-- 	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
--       // Your code goes here.
--       // ...
--       // ...
--    }//end repairRequest
INSERT INTO Repair(rID, hotelID, roomNo, mCompany, repairDate, description, repairType)
VALUES Repair(rID, hotelID, roomNo, mCompany, repairDate, description, repairType)
   
--    public static void numberOfAvailableRooms(DBProject esql){
-- 	  // Given a hotelID, get the count of rooms available 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end numberOfAvailableRooms
   
--    public static void numberOfBookedRooms(DBProject esql){
-- 	  // Given a hotelID, get the count of rooms booked
--       // Your code goes here.
--       // ...
--       // ...
--    }//end numberOfBookedRooms
   
--    public static void listHotelRoomBookingsForAWeek(DBProject esql){
-- 	  // Given a hotelID, date - list all the rooms available for a week(including the input date) 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end listHotelRoomBookingsForAWeek
   
--    public static void topKHighestRoomPriceForADateRange(DBProject esql){
-- 	  // List Top K Rooms with the highest price for a given date range
--       // Your code goes here.
--       // ...
--       // ...
--    }//end topKHighestRoomPriceForADateRange
   
--    public static void topKHighestPriceBookingsForACustomer(DBProject esql){
-- 	  // Given a customer Name, List Top K highest booking price for a customer 
--       // Your code goes here.
--       // ...
--       // ...
--    }//end topKHighestPriceBookingsForACustomer
   
--    public static void totalCostForCustomer(DBProject esql){
-- 	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
--       // Your code goes here.
--       // ...
--       // ...
--    }//end totalCostForCustomer
   
--    public static void listRepairsMade(DBProject esql){
-- 	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
--       // Your code goes here.
--       // ...
--       // ...
--    }//end listRepairsMade
   
--    public static void topKMaintenanceCompany(DBProject esql){
-- 	  // List Top K Maintenance Company Names based on total repair count (descending order)
--       // Your code goes here.
--       // ...
--       // ...
--    }//end topKMaintenanceCompany
   
--    public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
-- 	  // Given a hotelID, roomNo, get the count of repairs per year
--       // Your code goes here.
--       // ...
--       // ...
--    }//end listRepairsMade