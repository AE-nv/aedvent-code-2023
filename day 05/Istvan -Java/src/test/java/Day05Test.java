import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class Day05Test {

    @Test
    void mapping() {
        var line = "50 98 2";
        var mapping = Day05.createMapping(line);
        assertThat(mapping).isNotNull();
        assertThat(mapping.map(50L)).isEqualTo(-1L);
        assertThat(mapping.map(98L)).isEqualTo(50L);
        assertThat(mapping.map(99L)).isEqualTo(51L);
        assertThat(mapping.map(100L)).isEqualTo(-1L);
        assertThat(mapping.map(Long.MAX_VALUE)).isEqualTo(-1L);
    }

    @Test
    void MappingSet() {
        var set = new Day05.MappingSet("seed","soil");
        set.addMapping(Day05.createMapping("50 98 2"));
        set.addMapping(Day05.createMapping("52 50 48"));

        assertThat(set.sourceType()).isEqualTo("seed");
        assertThat(set.destinationType()).isEqualTo("soil");

        assertThat(set.map(98L)).isEqualTo(50L);
        assertThat(set.map(99L)).isEqualTo(51L);
        assertThat(set.map(50L)).isEqualTo(52L);
        assertThat(set.map(97L)).isEqualTo(99L);
        assertThat(set.map(45L)).isEqualTo(45L);
        assertThat(set.map(Long.MAX_VALUE)).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    void partA() throws IOException {
        assertThat(Day05.partA("testInput.txt")).isEqualTo(35L);
    }
    @Test
    void partB() throws IOException {
        assertThat(Day05.partB("testInput.txt")).isEqualTo(46L);
    }
}
