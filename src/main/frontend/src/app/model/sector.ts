export interface Sector {
    id: number;
    parentId: number;
    name: string;
    children: Sector[]
    level: number
    isSelected: boolean
}