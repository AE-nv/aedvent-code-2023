from dataclasses import dataclass, field
from typing import List
from range import Range, DumbRange


@dataclass
class Mapper:
    ranges: List[Range] = field(default_factory=list)

    def add_range(self, new_range: Range):
        self.ranges.append(new_range)

    def add_range_from_string_numbers(self, source_range: int, target_range: int, length: int):
        self.add_range(Range(source_range, target_range, length))

    def sort_ranges(self):
        self.ranges = sorted(self.ranges, key=lambda x: x.start)

    def map_source_to_target(self, source_value: int) -> int:
        for i, range_inner in enumerate(self.ranges):
            if range_inner.is_in_range(source_value):
                return range_inner.map(source_value)
        return source_value

    def map_range_to_ranges(self, source_range: DumbRange) -> List[DumbRange]:
        all_ranges = [source_range]
        mapped_ranges = []
        for range_inner in self.ranges:
            to_be_checked = all_ranges.pop(-1)
            splitted_ranges, mapped_ranges_out = self.split_dumb_range(range_inner, to_be_checked)
            mapped_ranges.extend(mapped_ranges_out)
            all_ranges.extend(splitted_ranges)
            if len(splitted_ranges) == 0:
                # to_be_checked = all_ranges.pop(-1)
                break
            if len(splitted_ranges) == 1 and splitted_ranges[0] == to_be_checked:
                continue
        if len(all_ranges) == 0 and len(mapped_ranges) == 0:
            return [to_be_checked]
        return all_ranges + mapped_ranges

    def map_ranges_to_ranges(self, source_ranges: List[DumbRange]) -> List[DumbRange]:
        all_ranges = []
        for source_range in source_ranges:
            all_ranges.extend(self.map_range_to_ranges(source_range))
        return all_ranges

    @staticmethod
    def split_dumb_range(inner_range: Range, in_range: DumbRange):
        all_ranges = []
        mapped_ranges = []
        dumb_range = DumbRange(in_range.start, in_range.end)
        if dumb_range.start < inner_range.start:
            if dumb_range.end > inner_range.start:
                all_ranges.append(DumbRange(dumb_range.start, inner_range.start))
            else:
                all_ranges.append(DumbRange(dumb_range.start, dumb_range.end))
            dumb_range.start = inner_range.start
        if dumb_range.end > inner_range.start + inner_range.length:
            if dumb_range.start < inner_range.start + inner_range.length:
                all_ranges.append(DumbRange(inner_range.start + inner_range.length, dumb_range.end))
            else:
                all_ranges.append(DumbRange(dumb_range.start, dumb_range.end))
            dumb_range.end = inner_range.start + inner_range.length
        if inner_range.start <= dumb_range.start < inner_range.start + inner_range.length:
            start_diff = dumb_range.start - inner_range.start
            diff = dumb_range.end - dumb_range.start
            mapped_ranges.append(DumbRange(inner_range.destination + start_diff, inner_range.destination + start_diff + diff))
        return all_ranges, mapped_ranges





@dataclass
class MapperBuilder:
    map_dict: dict = field(default_factory=dict)
    map_to_map_dict: dict = field(default_factory=dict)
    seeds: List[DumbRange] = field(default_factory=list)

    @staticmethod
    def _read_seeds(line: str):
        seeds = line.split(":")[1]
        seeds = seeds.strip()

        seeds_temp = [int(seed) for seed in seeds.split(" ")]
        seeds_start = [i for index, i in enumerate(seeds_temp) if index % 2 == 0]
        seeds_end = [i for index, i in enumerate(seeds_temp) if index % 2 == 1]
        seeds = zip(seeds_start, seeds_end)
        all_seeds = []
        for seed_start, seed_end in seeds:
            all_seeds.append(DumbRange(seed_start, seed_start + seed_end))
        return all_seeds

    def _add_map_definition(self, line: str):
        line = line.split(" ")[0]
        source, target = line.split("-to-")
        self.map_to_map_dict[source] = target
        self.map_dict[source] = Mapper()
        return source

    def create_map_for_input(self, path: str):
        current_map = None
        with open(path) as f:
            for line in f.read().split("\n"):
                if line == "":
                    continue
                elif "seeds" in line:
                    self.seeds = self._read_seeds(line)
                elif "map" in line:
                    current_map = self._add_map_definition(line)
                else:
                    target, source, length = line.split(" ")
                    self.map_dict[current_map].add_range_from_string_numbers(int(source), int(target), int(length))
        for map_inner in self.map_dict.values():
            map_inner.sort_ranges()

    def map_seeds_to_location(self):
        all_locations = []
        for seed_iter in self.seeds:
            all_locations.append(self.map_seed_to_location(seed_iter))
        return all_locations

    def map_seeds_range_to_location_ranges(self):
        all_locations = []
        for seed_range in self.seeds:
            all_locations.extend(self.map_seed_range_to_location_ranges(seed_range))
        return all_locations

    def map_seed_to_location(self, seed_inner):
        print(f"Mapping seed {seed_inner}")
        current_source = "seed"
        current_destination = None
        mapped_value = seed_inner
        while current_destination != "location":
            current_destination = self.map_to_map_dict[current_source]
            mapped_value = self.map_dict[current_source].map_source_to_target(mapped_value)
            current_source = current_destination
        return mapped_value

    def map_seed_range_to_location_ranges(self, seed_range: DumbRange):
        print(f"Mapping seed range {seed_range}")
        current_source = "seed"
        current_destination = None
        mapped_value = seed_range
        seed_range_to_use = [seed_range]
        while current_destination != "location":
            current_destination = self.map_to_map_dict[current_source]
            mapped_values = self.map_dict[current_source].map_ranges_to_ranges(seed_range_to_use)
            current_source = current_destination
            seed_range_to_use = mapped_values
        return seed_range_to_use

if __name__ == '__main__':
    mapper_builder = MapperBuilder()
    mapper_builder.create_map_for_input("input.txt")
    locations = mapper_builder.map_seeds_range_to_location_ranges()
    print(min(locations, key=lambda x: x.start))
    # locations = mapper_builder.map_seeds_to_location()
    # print(min(locations))
