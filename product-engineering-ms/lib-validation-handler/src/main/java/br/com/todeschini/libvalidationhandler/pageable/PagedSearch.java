package br.com.todeschini.libvalidationhandler.pageable;

public interface PagedSearch<T>{

    <T> T search(PageableRequest request);
}
