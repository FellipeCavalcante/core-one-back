package com.coreone.back.dto.sector;

import com.coreone.back.domain.Task;
import com.coreone.back.domain.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetSubSectorResponseBySector {
    private UUID id;
    private String name;
}
