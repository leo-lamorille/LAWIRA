package heavynimbus.backend.db.stats;

import heavynimbus.backend.db.attributeOption.AttributeOptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeOptionStatsResponse {
  private String optionValue;
  private AttributeOptionType optionType;
  private Double commandOccurrences;
  private Double configurationOccurrences;
}
