package com.bloff.pharmacy.app.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.bloff.pharmacy.app.dao.DestinyRepository;
import com.bloff.pharmacy.app.entity.Destiny;
import com.bloff.pharmacy.app.service.DestinyServiceImpl;

public class DestinyServiceTestsTests {

    @Mock
    private DestinyRepository repo;

    @InjectMocks
    private DestinyServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_find_all_destinies() {
        when(repo.findAll()).thenReturn(loadData());

        List<Destiny> destinies = service.findAll();

        assertThat(destinies.size()).isEqualTo(3);
        assertThat(destinies).isNotEmpty();
        assertThat(destinies).doesNotHaveDuplicates();
    }

    @Test
    public void should_find_destiny_by_id() {
        Long theId = 2L;

        when(repo.findById(theId)).thenReturn(Optional.of(loadData().get(theId.intValue() - 1)));

        Destiny destiny = service.findById(theId);

        assertThat(destiny.getId()).isSameAs(theId);
        assertThat(destiny).isNotNull();
    }

    @Test
    public void should_save_destiny() {
        Destiny destiny = new Destiny(4L, "Man");

        when(repo.save(Mockito.any(Destiny.class))).thenReturn(destiny);

        Destiny destinySaved = service.save(destiny);

        assertThat(destinySaved.getId()).isSameAs(destiny.getId());
        assertThat(destinySaved.getDestinyName()).isSameAs(destiny.getDestinyName());
        assertThat(destinySaved).isNotNull();
    }

    @Test
    public void should_delete_destiny_by_id() {
        Long theId = 2L;

        service.deleteById(theId);

        verify(repo, times(1)).deleteById(theId);
        verifyNoMoreInteractions(repo);
    }

    private List<Destiny> loadData() {
        List<Destiny> destinies = Arrays.asList(
                new Destiny(1L, "Adult"),
                new Destiny(2L, "Child"),
                new Destiny(3L, "Woman"));
        return destinies;
    }
}
