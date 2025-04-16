package com.devsuperior.dscommerce.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.tests.ProductFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	@Mock
	private ProductRepository repository;

	private Product product;
	private ProductDTO productDTO;
	private long existingId, nonExistingId, dependentProductId;
	private PageImpl<Product> page;

	@BeforeEach
	void SetUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentProductId = 3L;
		product = ProductFactory.createProduct("Panacota");
		productDTO = new ProductDTO(product);
		page = new PageImpl<>(List.of(product));

		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.when(repository.searchByName(any(), (Pageable) any())).thenReturn(page);

		Mockito.when(repository.save(any())).thenReturn(product);

		Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		Mockito.when(repository.existsById(dependentProductId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);

		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentProductId);

	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		ProductDTO result = service.findById(existingId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
		Assertions.assertEquals(result.getName(), product.getName());
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});

	}

	@Test
	public void findAllShouldReturnPagedProductMinDTO() {

		Pageable pageable = PageRequest.of(0, 12);
		Assertions.assertNotNull(service.findAll("Panacota", pageable));

	}

	@Test
	public void insertShouldReturnProductDTO() {
		ProductDTO result = service.insert(productDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), product.getId());
	}

	@Test
	public void updateShouldReturnProductWhenIdExists() {
		ProductDTO dto = service.update(existingId, productDTO);
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getId(), existingId);
		Assertions.assertEquals(dto.getName(), productDTO.getName());

	}

	@Test
	public void updateShouldThrowEntityNotFoundExceptionWhenIdDoesNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, productDTO);
		});

	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhendependentProductId() {

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentProductId);
		});
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExisting() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}

}
