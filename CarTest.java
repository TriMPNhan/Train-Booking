/**
 * The test class CarTest.
 *
 * @author  Lynn Marshall, SCE
 * @version 1.2 May 1st, 2015
 */
public class CarTest extends junit.framework.TestCase
{
    /**
     * Default constructor for test class CarTest
     */
    public CarTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }
    
    public void testCreateBusinessCar()
    {
        Car aCar = new Car(1385, true);
        Seat[] seats = aCar.seats();
        
        /* Verify that the car has the right number of seats. */
        assertEquals(Car.BUSINESS_SEATS, seats.length);
        
        /* Verify that each seat has the correct number and price. */
        for (int i = 0; i < seats.length; i++) {
            assertEquals(i+1, seats[i].number());
            assertEquals(Car.BUSINESS_SEAT_COST, seats[i].price());
        }
    }
    
    public void testCreateEconomyCar()
    {
        Car aCar = new Car(1400, false);
        Seat[] seats = aCar.seats();
        
        /* Verify that the car has the right number of seats. */        
        assertEquals(Car.ECONOMY_SEATS, seats.length);
        
        /* Verify that each seat has the correct number and price. */       
        for (int i = 0; i < seats.length; i++) {
            assertEquals(i+1, seats[i].number());
            assertEquals(Car.ECONOMY_SEAT_COST, seats[i].price());
        }
    }    
    
    public void testID()
    {
         Car aCar;
         aCar= new Car(1385, true);
         assertEquals(1385, aCar.id());
         aCar = new Car(1400, false);
         assertEquals(1400, aCar.id());
    }
    
    public void testIsBusinessClass()
    {
         Car aCar;
         aCar = new Car(1385, true);
         assertTrue(aCar.isBusinessClass());
         aCar = new Car(1400, false);
         assertFalse(aCar.isBusinessClass()); 
    }
    
    public void testBookNextSeat()
    {
        Car bCar;
        Car eCar;
        bCar = new Car(1234, true);
        eCar = new Car(1432, false);
        
        Seat[] eseats = eCar.seats();
        Seat[] bseats = bCar.seats();
        
        /* Verify that no seats are booked. */
        for (int i = 0; i < bseats.length; i++) {
            assertFalse(bseats[i].isBooked());
        }
        
        for (int i = 0; i < eseats.length; i++)
        {
            assertFalse(eseats[i].isBooked());
        }
        
        /* Verify that the seats are booked consecutively,
         * starting with Seat #1.
         */
        for (int i = 0; i < bseats.length; i++) {
            bseats = bCar.seats();
            assertFalse(bseats[i].isBooked()); // not booked
            assertTrue(bCar.bookNextSeat()); // book it
            assertTrue(bseats[i].isBooked()); // now booked
            if (i != bseats.length-1) {
                assertFalse(bseats[i+1].isBooked()); // but next isn't
            }
        }
        
        for (int i = 0; i < eseats.length; i++) {
            eseats = eCar.seats();
            assertFalse(eseats[i].isBooked()); // not booked
            assertTrue(eCar.bookNextSeat()); // book it
            assertTrue(eseats[i].isBooked()); // now booked
            if (i != eseats.length-1) {
                assertFalse(eseats[i+1].isBooked()); // but next isn't
            }
        }
        
        /* Try to book a seat now that all the seats have been booked. */
        assertFalse(bCar.bookNextSeat());
        assertFalse(eCar.bookNextSeat());
    }
    
    public void testCancelSeat()
    {
        Car bCar;
        bCar = new Car(1234, true);
        
        Car eCar;
        eCar = new Car (4321,false);
        
        Seat[] bseats = bCar.seats();
        Seat[] eseats = eCar.seats();
        
        /* Cancel seat 0. cancelSeat() should return false, as there
         * is no seat 0.
         */
        assertFalse(bCar.cancelSeat(0));
        assertFalse(eCar.cancelSeat(0));
        
        /* Try cancelling a seat whose number is one higher than 
         * the highest valid seat number (seats.length - 1). 
         * cancelSeat() should return false.
         */
        assertFalse(bCar.cancelSeat(bseats.length));
        assertFalse(eCar.cancelSeat(bseats.length));
        
        /* Try cancelling all the seats in the car, even though 
         * they haven't been booked. cancelSeat() should 
         * return false.
         */
        for (int i = 0; i < bseats.length; i++) {
            assertFalse(bCar.cancelSeat(i+1));
        }
        
        for (int i = 0; i < eseats.length; i++) {
            assertFalse(eCar.cancelSeat(i+1));
        }
        
        /* Book all the seats */
        for (int i = 0; i < bseats.length; i++) {
            bCar.bookNextSeat();
        }  
        
        for (int i = 0; i < eseats.length; i++) {
            eCar.bookNextSeat();
        }
        
        /* Try cancelling all the seats in the car.
         */
        for (int i = 0; i < bseats.length; i++) {
            assertTrue(bCar.cancelSeat(i+1));
        } 
        
        for (int i = 0; i < eseats.length; i++) {
            assertTrue(eCar.cancelSeat(i+1));
        } 
        
        /* In case seat numbers are off, try some more tests.
         */

        // book 2 seats
        assertTrue(bCar.bookNextSeat());
        assertTrue(bCar.bookNextSeat());
        
        assertTrue(eCar.bookNextSeat());
        assertTrue(eCar.bookNextSeat());
        
        // try to cancel the 3rd
        assertFalse(bCar.cancelSeat(3));
        
        assertFalse(eCar.cancelSeat(3));
        
        // cancel the 1st and 2nd (were both booked)
        assertTrue(bCar.cancelSeat(1));
        assertTrue(bCar.cancelSeat(2));
        
        assertTrue(eCar.cancelSeat(1));
        assertTrue(eCar.cancelSeat(2));
        
        // Cancel the 50th seat which doesn't exist
        assertFalse(bCar.cancelSeat(50));
        
        assertFalse(eCar.cancelSeat(50));
    }      
}
