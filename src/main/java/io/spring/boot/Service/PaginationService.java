package io.spring.boot.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationService {

    public <T> Page<T> getPage(List<T> items, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<T> currentPageItems;

        if (items.size() < startItem) {
            currentPageItems = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageSize, items.size());
            currentPageItems = items.subList(startItem, toIndex);
        }

        return new PageImpl<>(currentPageItems, pageable, items.size());
    }
}
