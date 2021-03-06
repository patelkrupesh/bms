User Controller : Table name : users
1.	Create user (types of user : CUSTOMER, ADMIN, BOXOFFICE)
      
      •	UI : Sign up page -> enter user details.
      
      •	API : userSignUp(userdetails)(POST : /api/user/signUp)
      
      •	DB : CREATE_USER(userdetails)
2.	Login
      
      •	UI : Login page
      
      •	API : loginUser(username, password) (GET : /api/user/login)
      
      //	ideally we will encrypt password.
      
      //	db call will get encrypted password from db and match
      
      //	If match is successful, create a auth-token using : userid, user type, token expiry time  ideally we will only keep the login apis open and for all other apis, user need to enter this auth token which they receive from login api’s response. (won’t do in this project)

We can also open the apis to search the tickets and theatres and only booking api requires user auth token.
3.	Edit user
      
      •	UI: login -> Goto edit profile page
      
      •	API: editUser(userDetails)(POST : /api/user/edit)
      
      •	DB : UPDATE_USER(userdetails)

4. Get all booking
   
   •    UI: login -> Goto profile page -> bookings
   
   •	API: getAllBookingsData()(GET : /api/user/allBookings)
   
   •	DB : READ_BOOKING(userdetails)

---------

Theatre/Screen/Show/Seat controllers : 

Table names : theatres, screens, shows, seats

4.	Theatre signup
      
      •	UI : Sign up page -> enter theatre basic details.
      
      •	API : theatreSignUp(details) (POST : /api/theatre/signup)
      
      •	DB : CREATE_THEATRE(details)
      
5.	Theatre login:
      
      •	UI : Theatre login page
      
      •	API : login(id, password) (GET : /api/theatre/login)
      
      •	DB : READ_PASSWORD(userid)
      
6.	Edit theatre data
      
      •	UI: login -> Goto edit theatre basic page
      
      •	API: editTheatre(details) (POST : /api/theatre/edit)
      
      •	DB : UPDATE_THEATRE(details)
      
7.	Get theatres in a city
      
      •	API: getTheatresByCity (GET : /api/theatre/theatreByCity)
      
      •	DB : READ_THEATRE(details)
      
8.	Get theatres showing particular movie
      
      •	UI : Login as normal user -> enter city name -> select movie from list -> select date -> call api to get all theatres in the city.
      
      •	API : getAllShows(city, movie, date) (GET: /api/shows/allshows)
      
      •	DB: READ_SHOWS(city, movie, date)

--------------

9.	Add screen:
      
      •	UI : login as theatre -> add screen
      
      •	API: addScreen (details) (POST: /api/screens/add)
      
      •	DB: CREATE_SCREEN(seatsObj), INSERT_THEATRE(theatreId, screenId)
      
10.	Edit screen:
       
       •	UI : login as theatre -> select screen ->edit screen
       
       •	API: editScreen (theatreId, screenId, seatsObject) (POST: /api/screens/edit)
       
       •	DB: UPDATE_SCREEN(screenId, seatsObj)
       
-------------

10.	Add show :
 
        •	UI : login as theatre -> add show ->enter movie, ticket price etc.
       
        •	API: addShow (showDto) (POST: /api/shows/add)

        •	DB: CREATE_SHOW(details), UPDATE_SEATS(details)

11.	Edit show:
       
       • edit basic show details
       
       • API: editShow (showDto) (GET: /api/shows/edit)
       
12.	Find movie show:
       
       •    UI : login as user -> search movie ->select movie, select seats.
       
       •	API: getAllShows (ShowInput) (GET: /api/shows/allshows)
       
13.	Get seats:
       
       •	UI: after above step (api12) -> select time -> select number of person
       
       •	API:  getAllSeats(screenId, movieTime)(GET: /api/seats/getallseats)
       
       •	DB: READ_SEATS(screenId, movieTime)
       
14.	Assign movie to screen (Add show) - as per api 10

-------

Table name : MOVIE

15.	Add new movie to database
       
       •	UI : Login as admin -> Add new movie -> Add cast, pgoto, description etc details
       
       •	API: addNewMovie(movieDetails) //add movieType = NEW to details
       (POST : /api/movies/add")
       
       •	DB: CREATE_MOVIE(movieDetails)
       
16.	Edit movie params
       
       •	UI : Login as admin -> Select movie -> Edit movie details (any detail except rating is editable)
       
       •	API: editMovie(movieDetails)(POST : /api/movies/edit")
       
       •	DB: UPDATE_MOVIE(movieDetails)
       
17.	Get list of movies
       
       •	UI : Login as customer -> Select city -> Get list of movies
       Or assign movie to screen flow
       
       •	API: getAllNewMovies(type) gets all movies with movie type (default value = new)
       (GET : /api/movies/allMovies")
       
       •	DB: READ_MOVIES(movieType)
       
       Note : we can write a cron job which will update all movies from new to old every Thursday night/ or after 2 weeks based on settings. Currently I am fetching all the movies.  (API: getAllMovies)

        For now I am fetching all the movies.        

------------------

Table name : BOOKING

18.	Reserve a ticket (create booking) (POST : /api/bookings/bookTicket)
       
       •	UI: select screen, time and seats that you want to book -> confirm and goto payment
       
       •	API: reserveTicket(showId, time, listOfSeats, amount , customerId)

       
       o	Mark the seats as – BOOKING_IN_PROGRESS (or booked = true) so that it shows unavailable for other users.
       
       o	Create entry in payment table, update seat details in db and  initiate payment
        // this 3 will be done as one transaction to avoid multiple person booking same ticket.
       
       o	i) If booking is successful , update seat as booked and return the ticket to user
            update booking table and seat entities.
       
       o	ii) If payment is failed, update seat as empty, update booking entity and return failure to user
       
       o	iii) If payment is timed out (& other cases like tab closed, internet issues etc):
       
       // There are 2 ways we can handle this.
       // 1 - we can craete a linked hashmap of on-going (not expired) bookings and update it on timeout
            this will create issue if the cluster goes down due to any issue or high load.
            or you want to work on multi node system, where other free nodes should be able to handle expired bookings.
            So I will go with the below approach 2
       
       // 2 - In all cases, while creating payment, we will crate a entry in bookingexpiry table with predefined 10 mins expiry time
            If payment fails/passes, (above cases 1 and ii ) we will remove the entry from this table. 
            
            for iii) case I have created a cron job - which will start a batch process every one minute
            It will get all the expired payments , check their status again with the payment gateway
            and handle them as below.
       
       •	At the end of timer check the payment status again.
       
       •	If payment is successful, change seat from BOOKING_IN_PROGRESS to BOOKED, 
            mark booking as SUCCESSFULL. update db (booking and seat) (in single transaction)
       
       •	If payment failed , then change seat to EMPTY (or is_booked = false)
            update the seat and booking (mark booking as FAILED) (in single transaction)
       
       •   If payment payment is still in progress, then register a recall request for the paymentId with gateway, mark payment as recalled/returned 
            update booking as failed and make seat as EMPTY.(in single transaction)
       
note : we can setup a queue (eg rabitmq) which will be fec by the status changes for SMS and email, upon booking successful we can send the ticket by email/whatsapp.

19.	Cancel booking : I have not implemented cancel booking as most of the theatres and booking websites do not support it, 
       but as per design we can support it. 
       
       In that case we will need to
       
       1 - create return payment and 
       
       2 – update booking and mark seat as EMPTY.

----------


Table name : PAYMENT

20.	Create payment // internal fn – have not exposed as api for this project
       •    createPayment
       •	DB : CREATE_PAYMENT (id, gateway, amount, customerId, bookingId, status)
21.	Update payment // internal fn
       •    updatePayment
       •	UPDATE_PAYMENT(id, status)
       
