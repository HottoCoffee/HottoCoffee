import type { components as UserComponents } from "@/swagger/schema/schemas/user";

export enum RoastLevel {
  light = "light",
  cinnamon = "cinnamon",
  medium = "medium",
  high = "high",
  city = "city",
  full_city = "full_city",
  french = "french",
  italian = "italian",
}

export enum GrindSize {
  finest = "finest",
  fine = "fine",
  mediumFine = "medium-fine",
  medium = "medium",
  coarse = "coarse",
}

export interface Post {
  post_id: number;
  user_info?: {
    user_id: number;
    account_id: string;
    display_name: string;
    icon_url: string;
  };
  location?: string;
  origin: string;
  way_to_brew?: string;
  roast_level?: RoastLevel;
  temperature?: number;
  grams_of_coffee?: number;
  grind_size?: GrindSize;
  impression?: string;
}

export type User = UserComponents["schemas"]["User"];
