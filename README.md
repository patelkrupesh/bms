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

Theatre/Screen/Show/Seat controller : 
Table name : theatres, screens, shows, seats
4.	Theatre signup
      •	UI : Sign up page -> enter theatre basic details.
      •	API : createTheatre(details)
      •	DB : CREATE_THEATRE(details)
5.	Theatre login:
      •	UI : Theatre login page
      •	API : loginUser(id, password) : same flow as user login
      •	DB : READ_PASSWORD(userid)
6.	Edit theatre data
      •	UI: login -> Goto edit theatre basic page
      •	API: editUser(details)
      •	DB : UPDATE_THEATRE(details)
7.	Get theatres in a city
      •	API: getTheatresByCity
8.	Get theatres showing particular movie
      •	UI : Login as normal user -> enter city name -> select movie from list ->  call api to get all theatres in the city.
      •	API : getMovieScreens(city, movie)
      •	DB: READ_MOVIE_SCREENS(city, movie)

9.	Add screen:
      •	UI : login as theatre -> add screen
      •	API: addScreen (theatreId, seatsObject)
      •	DB: CREATE_SCREEN(seatsObj), INSERT_THEATRE(theatreId, screenId)
10.	Edit screen:
       •	UI : login as theatre -> select screen ->edit screen
       •	API: editScreen (theatreId, screenId, seatsObject)
       •	DB: UPDATE_SCREEN(screenId, seatsObj)
10.	Add show:
      •	UI : login as theatre -> add show ->enter movie, ticket price etc.
      •	API: addShow (ShowInput)
      •	DB: CREATE_SHOW(details), UPDATE_SEATS(details)
11.	Edit show:
       • edit basic show details
12.	Find movie show:
       •    UI : login as user -> search movie ->select movie, select seats.
       •	API: getAllShows (ShowInput)
13.	Get seats:
       •	UI: after above step (api10) -> select time -> select number of person
       •	API:  getAllSeats(screenId, movieTime)
       •	DB: READ_SEATS(screenId, movieTime)
14.	Assign movie to screen (Add show)
       •	UI: login as admin -> select theatre -> select screen , select movie (get list of movies – api18), time and add price
       •	API: addMovietoScreen(screenId, movieId, time, price)
       •	DB: INSERT_MOVIE_TO_SCREEN(screenId, movieId, time, price)


Table name : MOVIE

15.	Add new movie to database
       •	UI : Login as admin -> Add new movie -> Add cast, pgoto, description etc details
       •	API: addNewMovie(movieDetails) //add movieType = NEW to details
       •	DB: CREATE_MOVIE(movieDetails)
16.	Edit movie params
       •	UI : Login as admin -> Select movie -> Edit movie details (any detail except rating is editable)
       •	API: editMovie(movieDetails)
       •	DB: UPDATE_MOVIE(movieDetails)
17.	Get list of movies
       •	UI : Login as customer -> Select city -> Get list of movies
       Or assign movie to screen flow
       •	API: getAllNewMovies(type) gets all movies with movie type (default value = new)
       •	DB: READ_MOVIES(movieType)
       Note : we can write a cron job which will update all movies from new to old every Thursday night/ or after 2 weeks based on settings. Currently I am fetching all the movies.  (API: getAllMovies)


Table name : BOOKING

18.	Reserve a ticket (create booking)
       •	UI: select screen, time and seats that you want to book -> confirm and goto payment
       •	API: reserveTicket(screenId, time, listOfSeats, paymentType , customerId)
       o	Mark the seats as – BOOKING_IN_PROGRESS
       o	Create entry in payment table and initiate payment
       o	If booking is successful , update seat as booked and return the ticket to user
       o	If payment is failed, update seat as empty and return failure to user
       o	If payment is timed out (& other cases like tab closed, internet issues etc):
       •	Set a timer of 10 minutes while creating payment
       •	At the end of timer check the payment status again.
       •	If payment is successful, change seat from BOOKING_IN_PROGRESS to BOOKED
       •	If payment failed , then change to EMPTY
       •	If payment payment is still in progress, then create a recall request for the paymentId with gateway, mark payment as recalled/returned and make seat EMPTY.
       •	DB: transaction 1 :
       UPDATE_SCREEN(screenId, time, seats),
       CREATE_BOOKING(screenId, time, seats, paymentId, custId, status), CREATE_PAYMENT(id, gateway, amount, customerId, bookingId, status, …),

transaction 2:
UPDATE_PAYMENT(id, status, …)
UPDATE_BOOKING(screenId, time, seats, paymentId, ticket, custId, status)
UPDATE_SCREEN(screenId, time, seats)

note : we can setup a queue (eg rabitmq) which will be fec by the status changes for SMS and email, upon booking successful we can send the ticket by email/whatsapp.
19.	Cancel booking : I have not implemented cancel booking as most of the theatres and booking websites do not support it, but as per design we can support it. In that case we will need to 1- create return payment and 2 – update screen and mark seat as EMPTY.


Table name : PAYMENT

20.	Create payment // internal fn – have not exposed as api for this project
       •	DB : CREATE_PAYMENT (id, gateway, amount, customerId, bookingId, status)
21.	Update payment // internal fn
       •	UPDATE_PAYMENT(id, status)
