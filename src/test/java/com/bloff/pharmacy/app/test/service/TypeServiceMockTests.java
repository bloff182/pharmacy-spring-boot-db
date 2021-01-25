package com.bloff.pharmacy.app.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.bloff.pharmacy.app.dao.TypeRepository;
import com.bloff.pharmacy.app.entity.Type;
import com.bloff.pharmacy.app.service.TypeServiceImpl;

public class TypeServiceMockTests {

	@Mock
	private TypeRepository repo;
	
	@InjectMocks
	private TypeServiceImpl service;
	
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void should_find_all_type(){
		//given
        when(repo.findAll()).thenReturn(loadData());
		//when
		List<Type> list = service.findAll();
		
		//than
		assertThat(list.size()).isEqualTo(4);
		assertThat(list).isNotNull();
	}
	
	@Test
	public void should_find_type_byId() {
		//given
		Long typeId = 1L;
		
		Type type = new Type(typeId, "tabs");
		
		when(repo.findById(typeId)).thenReturn(Optional.of(type));
		
		//when
		Type actualType = service.findById(typeId);
		//then
		verify(repo, times(1)).findById(typeId);
		verifyNoMoreInteractions(repo);
		assertThat(actualType).isEqualTo(type);
		assertThat(actualType).isNotNull();
	}
	
	@Test
	public void should_not_find_type_by_id_should_throw_exception() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			
			Type type = new Type();
			
			when(repo.findById(type.getId())).thenThrow(new RuntimeException(""));
			
			service.findById(type.getId());
			
			verify(repo, times(1)).findById(type.getId());
			verifyNoInteractions(repo);
			
		});
	}
	
	@Test
	public void should_save_type() {
		//given
		Type type = new Type(1L, "tabs");
		
		when(repo.save(Mockito.any(Type.class))).thenReturn(type);
		
		//when
		Type saved = service.save(type);
		
		//then
		assertThat(saved.getTypeName()).isSameAs(type.getTypeName());
		verify(repo, times(1)).save(type);
		assertThat(service.save(type)).isNotNull();
		
	}
	
	@Test
	public void should_delete_type() {
		//given
		Type type = new Type(1L, "tabs");
		//when
		service.deleteById(type.getId());
		//then
		verify(repo, times(1)).deleteById(type.getId());
		verifyNoMoreInteractions(repo);
	}
	
	List<Type> loadData(){
		
		List<Type> list = new ArrayList<>();
		list.add(new Type(1L, "tabs"));
		list.add(new Type(2L, "kaps"));
		list.add(new Type(3L, "krople"));
		list.add(new Type(4L, "syrop"));
		
		return list;
	}
	
}
