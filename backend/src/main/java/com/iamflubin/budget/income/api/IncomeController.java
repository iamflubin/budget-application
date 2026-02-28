package com.iamflubin.budget.income.api;

import com.iamflubin.budget.income.application.DeleteIncomeUseCase;
import com.iamflubin.budget.income.application.GetIncomesUseCase;
import com.iamflubin.budget.income.application.SaveIncomeUseCase;
import com.iamflubin.budget.income.application.UpdateIncomeUseCase;
import com.iamflubin.budget.income.application.command.SaveIncomeCommand;
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
@RequestMapping("/v1/incomes")
@RequiredArgsConstructor
class IncomeController {
    private final SaveIncomeUseCase saveIncomeUseCase;
    private final UpdateIncomeUseCase updateIncomeUseCase;
    private final DeleteIncomeUseCase deleteIncomeUseCase;
    private final GetIncomesUseCase getIncomesUseCase;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody SaveIncomeCommand command) {
        final var id = saveIncomeUseCase.execute(command);
        final var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Page<IncomeResponse>> getIncomes(
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String name,
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

        final Page<IncomeResponse> page = getIncomesUseCase.execute(pageable.getPageNumber(),
                        pageable.getPageSize(), name, from, to, direction, sortFields)
                .map(IncomeResponse::from);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id,
                                       @Valid @RequestBody UpdateIncomeRequest request) {
        updateIncomeUseCase.execute(request.toCommand(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteIncomeUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
