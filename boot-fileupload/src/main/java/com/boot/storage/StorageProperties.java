package com.boot.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
@ConfigurationProperties("storage")
@Data
public class StorageProperties {
    String filepath;
}
