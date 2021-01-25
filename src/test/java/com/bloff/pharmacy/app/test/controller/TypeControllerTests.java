package com.bloff.pharmacy.app.test.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bloff.pharmacy.app.controller.TypeController;
import com.bloff.pharmacy.app.entity.Type;
import com.bloff.pharmacy.app.service.TypeService;
import com.bloff.pharmacy.app.service.UserService;

@WebMvcTest(TypeController.class)
public class TypeControllerTests {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@MockBean
	private TypeService typeService;
	
	@MockBean
	private UserService userService;
	
	@InjectMocks
	private TypeController typeController;
	
	@BeforeEach
	public void setUp() {
	
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	
	}

	@Test
	public void should_find_all_types() throws Exception {
		
		when(typeService.findAll()).thenReturn(loadListTypes());
		
		mockMvc.perform(get("/types/listTypes"))
						.andExpect(status().isOk())
						.andExpect(view().name("type/types-list"))
						.andExpect(model().attribute("types", hasSize(5)))
						.andDo(print());
		
		verify(typeService, times(1)).findAll();
		verifyNoMoreInteractions(typeService);
	}
	
	@Test
	public void should_display_add_form() throws Exception {
		
		mockMvc.perform(get("/types/addType"))
					.andExpect(status().isOk())
					.andExpect(view().name("type/addType-form"))
					.andExpect(model().attribute("type", hasProperty("id", nullValue())))
					.andExpect(model().attribute("type", hasProperty("typeName", nullValue())))
					.andDo(print());
		
		verifyZeroInteractions(typeService);	
	}
	
	@Test
	public void should_add_new_type() throws Exception {
		
		Type type = new Type("igła");
		when(typeService.save(Mockito.any(Type.class))).thenReturn(type);
		
		mockMvc.perform(post("/types/saveType")
				.param("typeName", "igła"))
				.andExpect(status().isMovedTemporarily())
				.andExpect(view().name("redirect:/types/listTypes"));
						
		ArgumentCaptor<Type> argumentCaptor = ArgumentCaptor.forClass(Type.class);
		verify(typeService, times(1)).save(argumentCaptor.capture());
		verifyNoMoreInteractions(typeService);
		
		Type theType = argumentCaptor.getValue();
		
		assertThat(theType.getTypeName(), is("igła"));
		assertNull(theType.getId());
		
	}
	
	@Test
	public void should_not_add_new_type_and_return_validation_error_for_typeName_field() throws Exception {
		mockMvc.perform(post("/types/saveType"))
					.andExpect(status().isOk())
					.andExpect(model().attributeHasFieldErrors("type", "typeName"))
					.andExpect(model().attribute("type", hasProperty("id", nullValue())))
					.andExpect(model().attribute("type", hasProperty("typeName", emptyOrNullString())));
		
		verifyZeroInteractions(typeService);
	}
	
	@Test
	public void should_update_type() throws Exception {
		Long id = 6L;
		Type typeUpdated = new Type(id, "drops");
		
		when(typeService.findById(id)).thenReturn(typeUpdated);
		when(typeService.save(Mockito.any(Type.class))).thenReturn(typeUpdated);
		
		mockMvc.perform(get("/types/updateType/{id}",id))
						.andExpect(status().isOk())
						.andExpect(view().name("type/addType-form"))
						.andExpect(model().attribute("type", hasProperty("id", is(6L))))
						.andExpect(model().attribute("type", hasProperty("typeName", is("drops"))))
						.andDo(print());
		
	
		verify(typeService, times(1)).findById(id);
		verify(typeService, times(1)).save(typeUpdated);
		verifyNoMoreInteractions(typeService);
	}
	
	@Test
	public void should_delete_type_by_id() throws Exception {
		Long id = 1L;
		
		mockMvc.perform(get("/types/deleteType/{id}",id))
						.andExpect(status().isMovedTemporarily())
						.andExpect(view().name("redirect:/types/listTypes"));
	
		verify(typeService, times(1)).deleteById(id);
		verifyNoMoreInteractions(typeService);
	
	}
	
	private List<Type> loadListTypes() {
		List<Type> types = Arrays.asList(
				new Type(1L, "Tabletki"),
				new Type(2L, "Syrop"),
				new Type(3L, "Krople"),
				new Type(4L, "Czopki"),
				new Type(5L, "Kapsułki"));
		return types;
	}

	
}
