package com.iamflubin.budget.expense.api;

import com.iamflubin.budget.expense.application.DeleteExpenseUseCase;
import com.iamflubin.budget.expense.application.GetExpensesUseCase;
import com.iamflubin.budget.expense.application.SaveExpenseUseCase;
import com.iamflubin.budget.expense.application.UpdateExpenseUseCase;
import com.iamflubin.budget.expense.application.command.SaveExpenseCommand;
import com.iamflubin.budget.expense.domain.ExpenseCategory;
import com.iamflubin.budget.shared.domain.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/v1/expenses")
@RequiredArgsConstructor
class ExpenseController {
    private final SaveExpenseUseCase saveExpenseUseCase;
    private final UpdateExpenseUseCase updateExpenseUseCase;
    private final DeleteExpenseUseCase deleteExpenseUseCase;
    private final GetExpensesUseCase getExpensesUseCase;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody SaveExpenseCommand command) {
        final var id = saveExpenseUseCase.execute(command);
        final var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponse>> getExpenses(
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ExpenseCategory category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false) LocalDate to) {
        final Sort sort = pageable.getSort();

        final String direction = sort.isSorted()
                ? sort.iterator().next().getDirection().name()
                : Sort.Direction.DESC.name();

        final String[] sortFields = sort.stream()
                .map(Sort.Order::getProperty)
                .toArray(String[]::new);

        final Page<ExpenseResponse> page = getExpensesUseCase.execute(pageable.getPageNumber(),
                        pageable.getPageSize(), name, category, from, to, direction, sortFields)
                .map(ExpenseResponse::from);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id,
                                       @Valid @RequestBody UpdateExpenseRequest request) {
        updateExpenseUseCase.execute(request.toCommand(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteExpenseUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
