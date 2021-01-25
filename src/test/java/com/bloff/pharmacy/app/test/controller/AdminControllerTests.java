package com.bloff.pharmacy.app.test.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bloff.pharmacy.app.controller.AdminController;
import com.bloff.pharmacy.app.entity.Medicine;
import com.bloff.pharmacy.app.entity.Role;
import com.bloff.pharmacy.app.entity.User;
import com.bloff.pharmacy.app.service.MedicineService;
import com.bloff.pharmacy.app.service.UserService;

@WebMvcTest(AdminController.class)
public class AdminControllerTests {

	private Authentication authentication;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private MedicineService medicineService;

	@MockBean
	private UserService userService;

	@InjectMocks
	private AdminController adminController;

	@Mock
	private User user;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		setUserData();
	}

	@Test
	@WithMockUser(username = "frank", roles = { "USER", "ADMIN" })
	public void should_check_role_admin_true() {
		assertThat(adminController.checkRole()).isTrue();

	}

	@Test
	@WithMockUser(username = "frank", roles = { "USER" })
	public void should_check_role_admin_false() {
		assertThat(adminController.checkRole()).isFalse();
	}

	@Test
	@WithMockUser(username = "frank", roles = { "USER" })
	public void should_load_username() {
		assertThat(adminController.loadUsername()).isEqualTo("frank");

		loadAuthentication();
		assertThat(authentication.getName()).isEqualTo(user.getUsername());

		System.out.println(authentication.getName());
		System.out.println(user.getUsername());
	}

	@Test
	@WithMockUser(username = "frank", roles = { "USER" })
	public void should_not_load_username() {
		assertThat(adminController.loadUsername()).isNotEqualTo("wrongUsername");
	}

	@Test
	public void should_display_main_userr_details() throws Exception {
		loadAuthentication();

		when(userService.findByUserName(authentication.getName())).thenReturn(user);

		mockMvc.perform(get("/api/welcome")).andExpect(status().isOk()).andExpect(view().name("welcome"))
				.andExpect(model().attribute("userFirstName", user.getFirstName()))
				.andExpect(model().attribute("userLastName", user.getLastName()))
				.andExpect(model().attribute("userEmail", user.getEmail()))
				.andExpect(model().attribute("userIdW", user.getId()));

		verify(userService, times(4)).findByUserName(authentication.getName());
		verifyNoMoreInteractions(userService);

	}

	@Test
	public void should_display_list_of_all_medicines() throws Exception {
		loadAuthentication();

		when(medicineService.findAll()).thenReturn(loadMedicines());
		when(userService.findAllUsers()).thenReturn(setListUsers());

		mockMvc.perform(get("/api/listMedicine")).andExpect(status().isOk()).andDo(print())
				.andExpect(view().name("medicine/listMedicineForAdmin"))
				.andExpect(model().attribute("medicines", hasSize(5)))
				.andExpect(model().attribute("users", hasSize(5)));

		verify(medicineService, times(1)).findAll();
		verify(userService, times(1)).findAllUsers();
		verifyNoMoreInteractions(medicineService);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void should_find_all_medicines_for_user() throws Exception {

		String username = "tom";

		when(userService.findAllUsers()).thenReturn(setListUsers());
		when(medicineService.findAllByUsername(username))
				.thenReturn(Arrays.asList(loadMedicines().get(2), loadMedicines().get(4)));

		mockMvc.perform(get("/api/search").param("theSearchUsername", username)).andExpect(status().isOk())
				.andExpect(view().name("medicine/listMedicineForAdmin"))
				.andExpect(model().attribute("users", hasSize(5))).andExpect(model().attribute("medicines", hasSize(2)))
				.andDo(print());

		verify(userService, times(1)).findAllUsers();
		verify(medicineService, times(1)).findAllByUsername(username);

	}

	@Test
	public void should_not_find_medicines_if_we_do_not_give_name() throws Exception {

		mockMvc.perform(get("/api/search")).andExpect(status().isBadRequest());
	}
	
	private List<Medicine> loadMedicines() {
		List<Medicine> medicines = new ArrayList<>();
		medicines.add(new Medicine("Orofar", LocalDate.of(2022, 5, 25), 2, new User("mike")));
		medicines.add(new Medicine("nebudose", LocalDate.of(2022, 10, 18), 1, new User("katy")));
		medicines.add(new Medicine("frebusin", LocalDate.of(2022, 10, 18), 1, new User("tom")));
		medicines.add(new Medicine("controloc", LocalDate.of(2022, 9, 15), 1, new User("katy")));
		medicines.add(new Medicine("sambucus", LocalDate.of(2022, 9, 15), 1, new User("tom")));
		return medicines;
	}

	private User setUserData() {
		user.setId(1L);
		user.setUsername("john");
		user.setFirstName("John");
		user.setLastName("Smith");
		user.setEmail("john@gmail.com");
		user.setRoles(Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_EMPLOYEE")));
		return user;
	}

	private List<User> setListUsers() {
		List<User> users = Arrays.asList(
				new User("mary", "Mary", "Smith", "mary@gmail.com"),
				new User("ann", "Ann", "Lee", "ann@gmail.com"), 
				new User("paul", "Paul", "Wagner", "paul@gmail.com"),
				new User("tom", "Tom", "Flower", "tom@gmail.com"),
				new User("mike", "Mike", "Newman", "mike@gmail.com"));

		return users;
	}

	private void loadAuthentication() {
		authentication = mock(Authentication.class);

		authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
