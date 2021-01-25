Projekt apteczki z baza danych gdzie w tabeli medicine jest user_id

Simple CRUD application home pharmacy
user should have possibility to display, add new product, update and delete 
Project created in maven using Spring boot
Goals to do:
1.  functionality CRUD
	producent crud for all - done
	destiny crud for admin
	type crud for admin
	registration email verify - if exist user cannot be registered
	
2. update validation
3. Tests:
	a)integrations
	b)controllers 
		AdminController - done
		MedicineController
		ProducentController
		RegisterController
		SecurityController
		TypeController
		UserController
	c)Service
		DestinyService - done - handle exception
		MedicineService
		ProducentService
		TypeService - done - handle exception
		UserService
	d)Repository - handle tests with exceptions
		DestinyRepository - done
		MedicineRepository - done
		ProducentRepository - done
		TypeRepository - done
		UserRepository - done
		RoleRepository - done
TO DO

1. 	MedicineController - return null for user when go from list of producents to add medicine form
	add medicine zwraca obiekt null dla user gdy przechodze z listy producentow do formularza add medicine

2. Check if email exist in db, if yes user cannot be registered
	sprawdzenie czy email istnieje w bazie, jesli tak to nie można zarejestrowac uzytkownika

3. add to view check  validation like in registration form - add for add-form : medicine, destiny, type, producent

Done:
1.  display window with logged in user's data and button to update the data - bug: 
	endpoint of update user method expose user id - done



