# Auth Service
Name: auth-service  

Queries: 
...

Commands:
* User login 
POST /api/v1/auth/login { email, password }
* User registration
POST /api/v1/auth/register { firstName, lastName, email, password }

Events: 
...

# User Service
Name: user-service  

Queries: 
* Get user info 
GET /api/v1/users/me

Commands:
* Update user info
PUT /api/v1/users/me
* Delete profile
DELETE /api/v1/users/me

Events: 
...

# Hotel Service 
Name: hotel-service  

Queries: 
* Search hotels
GET /api/v1/hotels?search=test-hotel&checkInFrom=12-12-2020
* Get hotel details
GET /api/v1/hotels/{id}

Commands:
* Create hotel
POST /api/v1/hotels { name, city } Role: Admin
* Create rooms
POST /api/v1/hotels/{id}/rooms
* Update room
PUT /api/v1/hotels/{id}/rooms/{roomId}

Events: 
...

# Booking Service 
Name: booking-service  

Queries: 
* Get user bookings
GET /api/v1/bookings

Commands:
* Create booking
POST /api/v1/bookings { userId, hotelId, roomId }
* Cancel booking
DELETE /api/v1/bookings/{id} { userId, hotelId, roomId }

Events: 
BookingCreatedEvent
BreakfastReservedEvent
VehicleReservedEvent

# Kitchen Service 
Name: kitchen-service  

# Vehicle Service 
Name: vehicle-service  